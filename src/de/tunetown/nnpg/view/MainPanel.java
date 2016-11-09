package de.tunetown.nnpg.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.tunetown.nnpg.main.Main;
import de.tunetown.nnpg.view.control.ControlPanel;
import de.tunetown.nnpg.view.data.DataPanel;
import de.tunetown.nnpg.view.topology.TopologyPanel;

/**
 * Main UI panel, holding all components
 * 
 * @author xwebert
 *
 */
public class MainPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private Main main;
	private JFrame frame;
	private TopologyPanel netView;
	private DataPanel outView;
	private ControlPanel controlView;

	public MainPanel(Main main, JFrame frame) {
		super(new BorderLayout(3,3));
		this.main = main;
		this.frame = frame;
		
		init();
	}
	
	private void init() {
		// Visualization of Topology
		netView = new TopologyPanel(main, frame);
		add(netView, BorderLayout.CENTER);
		
		// Output graph
		outView = new DataPanel(main);
		add(outView, BorderLayout.EAST);
		
		controlView = new ControlPanel(main, frame);
		add(controlView, BorderLayout.SOUTH);
	}
	
	public ControlPanel getControlPanel() {
		return controlView;
	}
	
	public DataPanel getDataPanel() {
		return outView;
	}

	public TopologyPanel getTopologyPanel() {
		return netView;
	}
}
