package plot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.*;

public class PlotView extends JPanel {

	public static final int START_X = 380;
	public static final int START_Y = 300;

	static double zoom = 40D;
	static final int PLOT_WIDTH = Model.WIDTH - 50;
	static final int PLOT_HEIGHT = Model.HEIGHT - 120;
	static final int START_ZOOM = 40;

	private static final long serialVersionUID = -4235855917686387833L;
	private static final int POINT_AMOUNT = InputController.rightBound
			- InputController.leftBound + 1;
	private static final double ZOOM_FACTOR = 1.2;
	private static final double MAX_ZOOM = 200;
	private static final double MIN_ZOOM = 20;

	public int[] xPoints = new int[PLOT_WIDTH];
	public int[] yPoints = new int[PLOT_WIDTH];

	public PlotView(int width, int height) {
		setPreferredSize(new Dimension(width, height));
		setOpaque(true);
	}

	public PlotView() {
		setPreferredSize(new Dimension(PLOT_WIDTH, PLOT_HEIGHT));
		setOpaque(true);
	}

	public void paint(Graphics g) {
		super.paint(g);

		drawCoordinateSystem(g);

		g.setColor(Color.black);
		g.drawPolyline(xPoints, yPoints, POINT_AMOUNT);
	}

	public void drawCoordinateSystem(Graphics g) {
		g.setColor(Color.lightGray);
		g.drawLine(0, START_Y, PLOT_WIDTH, START_Y);
		g.drawLine(START_X, 0, START_X, PLOT_HEIGHT);

		for (int x = START_X - (int) zoom; x > zoom; x -= (int) zoom) {
			String num = "" + (x - START_X) / (int) zoom;
			g.drawString(num, x, START_Y);
		}

		for (int x = START_X; x < PLOT_WIDTH - zoom; x += (int) zoom) {
			String num = "" + (x - START_X) / (int) zoom;
			g.drawString(num, x, START_Y);
		}

		for (int y = START_Y - (int) zoom; y > zoom; y -= (int) zoom) {
			String num = "" + (START_Y - y) / (int) zoom;
			if (!num.equals("0"))
				g.drawString(num, START_X, y);
		}

		for (int y = START_Y; y < PLOT_HEIGHT - zoom; y += (int) zoom) {
			String num = "" + (START_Y - y) / (int) zoom;
			if (!num.equals("0"))
				g.drawString(num, START_X, y);
		}
	}

	public void clear() {
		getGraphics().clearRect(0, 0, PLOT_WIDTH, PLOT_HEIGHT);
		drawCoordinateSystem(getGraphics());
	}

	public void zoomIn() {
		if (zoom * ZOOM_FACTOR < MAX_ZOOM) {
			zoom *= ZOOM_FACTOR;

			try {
				InputController.action();
			} catch (Exception e) {
				InputController.showErrorMessage();
			}
		}
	}

	public void zoomOut() {
		if (zoom / ZOOM_FACTOR > MIN_ZOOM) {
			zoom /= ZOOM_FACTOR;

			try {
				InputController.action();
			} catch (Exception e) {
				InputController.showErrorMessage();
			}
		}
	}
}
