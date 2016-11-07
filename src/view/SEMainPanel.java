package view;

import java.awt.BorderLayout;
import javax.swing.JPanel;

public class SEMainPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private SEFrame frame;
	private SENetView netView;
	private SEOutputView outView;
	private SEControlPanel controlView;

	public SEMainPanel(SEFrame frame) {
		super(new BorderLayout(3,3));
		this.frame = frame;
		
		init();
	}
	
	private void init() {
		// Visualization of Topology
		netView = new SENetView(frame);
		add(netView, BorderLayout.CENTER);
		
		// Output graph
		outView = new SEOutputView(frame);
		add(outView, BorderLayout.EAST);
		
		controlView = new SEControlPanel(frame);
		add(controlView, BorderLayout.SOUTH);
	}
	
	public SEControlPanel getControlPanel() {
		return controlView;
	}
}
