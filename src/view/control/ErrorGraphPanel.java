package view.control;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Collections;
import javax.swing.JComponent;
import main.Main;

public class ErrorGraphPanel extends JComponent {
	private static final long serialVersionUID = 1L;

	private Main main;
	
	public ErrorGraphPanel(Main main) {
		this.main = main;

/*		Dimension dim = this.getSize(); //new Dimension(250, 100);
		this.setPreferredSize(dim);
		this.setMinimumSize(dim);
		this.setSize(dim);*/
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.black);
		g.drawRect(0, 0, getWidth(), getHeight());
		
		if (main.getTracker().getRmsErrors().size() == 0) return;
		
		try {
			double xstep = getWidth() / (double)main.getTracker().getRmsErrors().size();
			double ystep = getHeight() / Collections.max(main.getTracker().getRmsErrors());
			
			for (int i=1; i<main.getTracker().getRmsErrors().size(); i++) {
				double err = main.getTracker().getRmsErrors().get(i);
				double errm1 = main.getTracker().getRmsErrors().get(i-1);
				
				g.drawLine((int)((i-1)*xstep), getHeight()-(int)(errm1*ystep),
						(int)(i*xstep), getHeight()-(int)(err*ystep));
			}
		} catch (Exception e) {
			
		}
	}
}
