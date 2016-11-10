package de.tunetown.nnpg.view.control;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;

import de.tunetown.nnpg.main.Main;
import de.tunetown.nnpg.view.ViewProperties;

/**
 * UI component for showing the dynamic error graph in the statistics section
 * 
 * @author xwebert
 *
 */
public class ErrorGraphPanel extends JComponent {
	private static final long serialVersionUID = 1L;

	private Main main;
	
	public ErrorGraphPanel(Main main) {
		this.main = main;
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getWidth(), getHeight());
		
		if (main.getTracker().getIterations() == 0) return;
		
		// Calculation speed
		g.setColor(ViewProperties.ERRORGRAPH_COLOR_SPEED);
		paintGraph(g, getDoubleList(main.getTracker().getSpeeds()));

		// Training data
		g.setColor(ViewProperties.ERRORGRAPH_COLOR_TRAINING_ERROR);
		paintGraph(g, main.getTracker().getTrainingErrors());

		// Test data
		g.setColor(ViewProperties.ERRORGRAPH_COLOR_TEST_ERROR);
		paintGraph(g, main.getTracker().getTestErrors());
}
	
	private List<Double> getDoubleList(List<Long> list) {
		List<Double> ret = new ArrayList<Double>();
		for(int i=0; i< list.size(); i++) {
			ret.add(new Double(list.get(i)));
		}
		return ret;
	}

	private void paintGraph(Graphics g, List<Double> errors) {
		try {
			double xstep = getWidth() / (double)errors.size();
			double ystep = getHeight() / Collections.max(errors);
			
			for (int i=1; i<errors.size(); i++) {
				double err = errors.get(i);
				double errm1 = errors.get(i-1);
				
				g.drawLine((int)((i-1)*xstep), getHeight()-(int)(errm1*ystep),
						(int)(i*xstep), getHeight()-(int)(err*ystep));
			}
		} catch (Exception e) {
			
		}
	}
}
