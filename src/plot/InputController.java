package plot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

class InputController implements ActionListener {

	static final int OFFSET = 5;

	static int leftBound = OFFSET;
	static int rightBound = PlotView.PLOT_WIDTH - OFFSET;

	private static StringBuilder function;
	private static Invocable inv;

	@Override
	public void actionPerformed(ActionEvent event) {
		PlotView.zoom = PlotView.START_ZOOM;

		function = toJavaFunction(Model.function.getText());

		try {
			ScriptEngineManager factory = new ScriptEngineManager();
			ScriptEngine engine = factory.getEngineByName("JavaScript");

			String script = "function func(x) {return " + function + ";}";
			engine.eval(script);
			inv = (Invocable) engine;

			action();
		} catch (ScriptException) {
			showErrorMessage();
		} catch (NoSuchMethodException e) {
			showErrorMessage();
		}
	}

	public static void action() throws NoSuchMethodException, ScriptException {
		calculatePoints();
		Model.plot.paint(Model.plot.getGraphics());
	}

	public static void showErrorMessage() {
		JOptionPane.showMessageDialog(new JFrame(), "Invalid function "
				+ function, "Error", JOptionPane.ERROR_MESSAGE);
	}

	static void calculatePoints() throws NoSuchMethodException, ScriptException {
		leftBound = OFFSET;
		rightBound = Model.plot.getWidth() - OFFSET;

		for (int x = leftBound; x <= rightBound; x++) {
			Model.plot.xPoints[x - leftBound] = x;
			Model.plot.yPoints[x - leftBound] = getY(x);
		}
	}

	private static int getY(int x) throws NoSuchMethodException,
			ScriptException {
		double arg = ((double) (x - PlotView.START_X)) / PlotView.zoom;

		double funcValue = (Double) inv.invokeFunction("func", arg);
		int result = PlotView.START_Y - (int) (funcValue * PlotView.zoom);

		return result;
	}

	private StringBuilder toJavaFunction(String str) {
		StringBuilder result = new StringBuilder(
				str.replaceAll("(?<!\\pL)(?=\\pL)sin(?<=\\pL)(?!\\pL)",
						"Math.sin")
						.replaceAll("(?<!\\pL)(?=\\pL)cos(?<=\\pL)(?!\\pL)",
								"Math.cos")
						.replaceAll("(?<!\\pL)(?=\\pL)tg(?<=\\pL)(?!\\pL)",
								"Math.tan")
						.replaceAll("(?<!\\pL)(?=\\pL)abs(?<=\\pL)(?!\\pL)",
								"Math.abs")
						.replaceAll("(?<!\\pL)(?=\\pL)exp(?<=\\pL)(?!\\pL)",
								"Math.exp")
						.replaceAll("(?<!\\pL)(?=\\pL)arctg(?<=\\pL)(?!\\pL)",
								"Math.atan")
						.replaceAll("(?<!\\pL)(?=\\pL)arcsin(?<=\\pL)(?!\\pL)",
								"Math.asin")
						.replaceAll("(?<!\\pL)(?=\\pL)arccos(?<=\\pL)(?!\\pL)",
								"Math.acos")
						.replaceAll("(?<!\\pL)(?=\\pL)ln(?<=\\pL)(?!\\pL)",
								"Math.log")
						.replaceAll("(?<!\\pL)(?=\\pL)sqrt(?<=\\pL)(?!\\pL)",
								"Math.sqrt"));

		Pattern p = Pattern.compile("\\(([^()]+)\\)\\^([\\w\\.]+)");
		result = replacePow(p, result);

		p = Pattern.compile("(\\([^()]*\\([^()]+\\)[^()]*\\))\\^([\\w\\.]+)");
		result = replacePow(p, result);

		p = Pattern.compile("([\\w\\.]+)\\^([\\w\\.]+)");
		result = replacePow(p, result);

		return result;
	}

	private StringBuilder replacePow(Pattern p, StringBuilder str) {
		Matcher m = p.matcher(str);

		int idx = 0;
		while (m.find(idx)) {
			String replacement = "Math.pow(" + m.group(1) + ", " + m.group(2)
					+ ")";
			str.replace(m.start(), m.end(), replacement);

			idx = m.start() + replacement.length();
		}

		return str;
	}
}