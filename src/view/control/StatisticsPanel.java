package view.control;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Main;

public class StatisticsPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private Main main;
	
	private JLabel iterations;
	private JLabel rmsError;
	private ErrorGraphPanel errorGraph;

	public StatisticsPanel(Main main) {
		this.main = main;
		
		setLayout(new BorderLayout(3,3));
		
		errorGraph = new ErrorGraphPanel(this.main);
		add(errorGraph, BorderLayout.CENTER);

		JPanel statsR = new JPanel();
		statsR.setLayout(new BoxLayout(statsR, BoxLayout.PAGE_AXIS));
		add(statsR,BorderLayout.EAST);
		
		iterations = new JLabel();
		statsR.add(iterations);
		setIteration(0);

		rmsError = new JLabel();
		statsR.add(rmsError);
		setRmsError(1);
		rmsError.setPreferredSize(new Dimension(100, rmsError.getPreferredSize().height));
		
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
	}
	
	private void setIteration(int i) {
		iterations.setText("Iterations: " + i);
	}

	private void setRmsError(double i) {
		DecimalFormat df = new DecimalFormat("#.##");
		rmsError.setText("RMS Error: " + df.format(i));
	}

	public void update() {
		setIteration(main.getTracker().getIterations());
		setRmsError(main.getNetwork().getRmsError(main.getData()));
	}
}
