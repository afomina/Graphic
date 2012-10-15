package plot;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Model {

	static final int WIDTH = 800;
	static final int HEIGHT = 700;

	static JFrame mainFrame;
	static JTextField function;
	static PlotView plot;

	private static final int TEXT_SIZE = 15;

	public static void main(String[] args) {
		mainFrame = new JFrame("PlotDemo");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		mainFrame.setResizable(false);
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

		JButton rightShiftBut = new JButton(">");
		rightShiftBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				plot.rightShift();
			}
		});

		JButton leftShiftBut = new JButton("<");
		leftShiftBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				plot.leftShift();
			}
		});

		JButton upShiftBut = new JButton("/\\");
		upShiftBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				plot.upShift();
			}
		});

		JButton downShiftBut = new JButton("\\/");
		downShiftBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				plot.downShift();
			}
		});

		JScrollPane plotScroll = new JScrollPane(plot,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		plotScroll.getHorizontalScrollBar().addAdjustmentListener(
				new AdjustmentListener() {

					@Override
					public void adjustmentValueChanged(AdjustmentEvent event) {
						int type = event.getAdjustmentType();

						if (type == AdjustmentEvent.UNIT_INCREMENT
								|| type == AdjustmentEvent.BLOCK_INCREMENT) {
							plot.rightShift();
						} else {
							plot.leftShift();
						}
					}
				});

		mainFrame.add(label);
		mainFrame.add(function);
		// mainFrame.add(plotScroll);
		mainFrame.add(plot);
		mainFrame.add(clearButton);
		mainFrame.add(closeButton);
		mainFrame.add(zoomInBut);
		mainFrame.add(zoomOutBut);
		mainFrame.add(leftShiftBut);
		mainFrame.add(rightShiftBut);
		mainFrame.add(upShiftBut);
		mainFrame.add(downShiftBut);
		mainFrame.setVisible(true);
	}
}
