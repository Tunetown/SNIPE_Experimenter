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
	
	private JLabel eta;
	private JLabel batchSize;
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
		
		eta = new JLabel();
		statsR.add(eta);

		batchSize = new JLabel();
		statsR.add(batchSize);

		iterations = new JLabel();
		statsR.add(iterations);

		rmsError = new JLabel();
		statsR.add(rmsError);
		rmsError.setPreferredSize(new Dimension(100, rmsError.getPreferredSize().height));
		
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		
		update();
	}
	
	private void setEta(double value) {
		DecimalFormat df = new DecimalFormat("#.#####");
		eta.setText("Eta: " + df.format(value));
	}

	private void setBatchSize(int value) {
		batchSize.setText("Batch Size: " + value);
	}

	private void setIteration(int i) {
		iterations.setText("Iterations: " + i);
	}

	private void setRmsError(double i) {
		DecimalFormat df = new DecimalFormat("#.##");
		rmsError.setText("RMS Error: " + df.format(i));
	}

	public void update() {
		setEta(main.getNetwork().getEta());
		setBatchSize(main.getNetwork().getBatchSize());
		setIteration(main.getTracker().getIterations());
		setRmsError(main.getNetwork().getRmsError(main.getData()));
	}
}
