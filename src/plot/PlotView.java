package plot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.*;

public class PlotView extends JPanel {

	public static int startX = 380;
	public static int startY = 300;
	public static final int START_X_0 = 380;
	public static final int START_Y_0 = 300;
	public static final int SHIFT = 50;

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

	public int[] xPoints = new int[POINT_AMOUNT];
	public int[] yPoints = new int[POINT_AMOUNT];

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
		g.drawLine(0, startY, PLOT_WIDTH, startY);
		g.drawLine(startX, 0, startX, PLOT_HEIGHT);

		for (int x = startX - (int) zoom; x > zoom; x -= (int) zoom) {
			String num = "" + (x - startX) / (int) zoom;
			g.drawString(num, x, startY);
		}

		for (int x = startX; x < PLOT_WIDTH - zoom; x += (int) zoom) {
			String num = "" + (x - startX) / (int) zoom;
			g.drawString(num, x, startY);
		}

		for (int y = startY - (int) zoom; y > zoom; y -= (int) zoom) {
			String num = "" + (startY - y) / (int) zoom;
			if (!num.equals("0"))
				g.drawString(num, startX, y);
		}

		for (int y = startY; y < PLOT_HEIGHT - zoom; y += (int) zoom) {
			String num = "" + (startY - y) / (int) zoom;
			if (!num.equals("0"))
				g.drawString(num, startX, y);
		}
	}

	public void clear() {
		getGraphics().clearRect(0, 0, PLOT_WIDTH, PLOT_HEIGHT);
		zoom = START_ZOOM;
		startX = START_X_0;
		startY = START_Y_0;

		drawCoordinateSystem(getGraphics());

		InputController.inv = null;
		Model.function.setText("");
	}

	public void zoomIn() {
		if (zoom * ZOOM_FACTOR < MAX_ZOOM) {
			zoom *= ZOOM_FACTOR;
			InputController.action();
		}
	}

	public void zoomOut() {
		if (zoom / ZOOM_FACTOR > MIN_ZOOM) {
			zoom /= ZOOM_FACTOR;
			InputController.action();
		}
	}

	public void rightShift() {
		startX += SHIFT;
		InputController.action();
	}

	public void leftShift() {
		startX -= SHIFT;
		InputController.action();
	}

	public void upShift() {
		startY -= SHIFT;
		InputController.action();
	}

	public void downShift() {
		startY += SHIFT;
		InputController.action();
	}
}
