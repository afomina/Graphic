package plot;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Model {

	static final int WIDTH = 850;
	static final int HEIGHT = 750;

	static JFrame mainFrame;
	static JTextField function;
	static PlotView plot;

	static JScrollBar horScrollBar;
	static JScrollBar vertScrollBar;
	static int scrollStartValue = 500;
	static int vertScrollValue = scrollStartValue;
	static int horScrollValue = scrollStartValue;
	int extent = 5;
	int scrollMin = 0;
	static int scrollMax = 1000;
	private int scrollBarWidth = 17;
	private static final int TEXT_SIZE = 15;

	public Model() {
		mainFrame = new JFrame("PlotDemo");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		mainFrame.setLayout(new FlowLayout());

		plot = new PlotView();

		function = new JTextField(TEXT_SIZE);
		function.addActionListener(new InputController());

		JLabel label = new JLabel("y = ");
		label.setLabelFor(function);

		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				plot.clear();
			}
		});

		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JButton zoomInBut = new JButton("+");
		zoomInBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				plot.zoomIn();

			}
		});
		zoomInBut.setMnemonic(KeyEvent.VK_ADD);

		JButton zoomOutBut = new JButton("-");
		zoomOutBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				plot.zoomOut();

			}
		});
		zoomOutBut.setMnemonic(KeyEvent.VK_SUBTRACT);

		horScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, scrollStartValue,
				extent, scrollMin, scrollMax);
		horScrollBar.setPreferredSize(new Dimension(PlotView.PLOT_WIDTH,
				scrollBarWidth));
		horScrollBar.addAdjustmentListener(new HorizontalScrollBarListener());

		vertScrollBar = new JScrollBar(JScrollBar.VERTICAL, scrollStartValue,
				extent, scrollMin, scrollMax);
		vertScrollBar.setPreferredSize(new Dimension(scrollBarWidth,
				PlotView.PLOT_HEIGHT));
		vertScrollBar.addAdjustmentListener(new VerticalScrollBarListener());

		mainFrame.add(label);
		mainFrame.add(function);
		mainFrame.add(plot);
		mainFrame.add(vertScrollBar);
		mainFrame.add(horScrollBar);

		mainFrame.add(clearButton);
		mainFrame.add(closeButton);
		mainFrame.add(zoomInBut);
		mainFrame.add(zoomOutBut);

		mainFrame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new Model();
			}
		});
	}
}
