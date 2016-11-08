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
	private JLabel error;
	private JLabel procTime;
	//private JLabel procPercentage;
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

		error = new JLabel();
		statsR.add(error);
		error.setPreferredSize(new Dimension(150, error.getPreferredSize().height));
		
		procTime = new JLabel();
		statsR.add(procTime);

		//procPercentage = new JLabel();
		//statsR.add(procPercentage);

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

	private void setError(double i) {
		DecimalFormat df = new DecimalFormat("#.##");
		error.setText("Training Error: " + df.format(i));
	}

	private void setProcTime(long t) {
		if (t < 1000000000) {
			procTime.setText("CPU: " + (t / 1000000) + "ms");
		} else {
			procTime.setText("CPU: " + (t / 1000000000) + "s");
		}
	}
	
	/*
	private void setProcPercentage(double perc) {
		DecimalFormat df = new DecimalFormat("#.#####");
		procPercentage.setText("CPU: " + df.format(perc) + "%");
	}*/
	
	public void update() {
		setEta(main.getNetwork().getEta());
		setBatchSize(main.getNetwork().getBatchSize());
		setIteration(main.getTracker().getIterations());
		setError(main.getNetwork().getError(main.getData()));
		setProcTime(main.getTracker().getProcessingTime());
		//setProcPercentage(main.getTracker().getProcessingPercentage());
	}
}
