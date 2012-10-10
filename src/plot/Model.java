package plot;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

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

		JButton zoomIn = new JButton("+");
		zoomIn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				plot.zoomIn();

			}
		});
		zoomIn.setMnemonic(KeyEvent.VK_ADD);

		JButton zoomOut = new JButton("-");
		zoomOut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				plot.zoomOut();

			}
		});
		zoomOut.setMnemonic(KeyEvent.VK_SUBTRACT);

		mainFrame.add(label);
		mainFrame.add(function);
		mainFrame.add(plot);
		mainFrame.add(clearButton);
		mainFrame.add(closeButton);
		mainFrame.add(zoomIn);
		mainFrame.add(zoomOut);
		mainFrame.setVisible(true);
	}
}
