package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Collections;

import javax.swing.JComponent;

public class SEErrorGraph extends JComponent {
	private static final long serialVersionUID = 1L;

	private SEFrame frame;
	
	public SEErrorGraph(SEFrame frame) {
		this.frame = frame;

		Dimension dim = new Dimension(250, 100);
		this.setPreferredSize(dim);
		this.setMinimumSize(dim);
		this.setSize(dim);
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.black);
		g.drawRect(0, 0, getWidth(), getHeight());
		
		if (frame.getNetwork().getErrorList().size() == 0) return;
		
		try {
			double xstep = getWidth() / (double)frame.getNetwork().getErrorList().size();
			double ystep = getHeight() / Collections.max(frame.getNetwork().getErrorList());
			
			for (int i=1; i<frame.getNetwork().getErrorList().size(); i++) {
				double err = frame.getNetwork().getErrorList().get(i);
				double errm1 = frame.getNetwork().getErrorList().get(i-1);
				
				g.drawLine((int)((i-1)*xstep), getHeight()-(int)(errm1*ystep),
						(int)(i*xstep), getHeight()-(int)(err*ystep));
			}
		} catch (Exception e) {
			
		}
	}
}
