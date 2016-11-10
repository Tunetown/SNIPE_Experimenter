package de.tunetown.nnpg.view.control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import de.tunetown.nnpg.main.Main;
import de.tunetown.nnpg.model.ModelProperties;
import de.tunetown.nnpg.view.data.DataPanel;

/**
 * COntrols for data manipulation.
 * 
 * @author xwebert
 *
 */
public class DataControlPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private Main main;
	private JFrame frame;

	public DataControlPanel(Main main, JFrame frame) {
		this.main = main;
		this.frame = frame;
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		// Paint selector
		JRadioButton toolPaint = new JRadioButton("Paint");
		toolPaint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					setTool(DataPanel.TOOL_PAINT);
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		});
		toolPaint.setSelected(true);
        JRadioButton toolErase = new JRadioButton("Erase");
        toolErase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					setTool(DataPanel.TOOL_ERASE);
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		});
		
        ButtonGroup tools = new ButtonGroup();
        tools.add(toolPaint);
        tools.add(toolErase);
        
        add(toolPaint);
        add(toolErase);
 
		// Reset button
		JButton resetData = new JButton("Reset Data");
		resetData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					resetData();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		});
		add(resetData);

		// Resplit button
		JButton resplitData = new JButton("Re-Split Data");
		resplitData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					resplitData();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		});
		add(resplitData);

		// Grow data button
		JButton growData = new JButton("Grow All Data");
		growData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					growData();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		});
		add(growData);

		// Reduce data button
		JButton reduceData = new JButton("Reduce All Data");
		reduceData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					reduceData();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		});
		add(reduceData);

        // Border around control area
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
	}
	
	/**
	 * Reset data
	 * 
	 */
	private void resetData() {
		int dialogResult = JOptionPane.showConfirmDialog(null, "Really delete current training data?", "Delete Training Data", JOptionPane.YES_NO_OPTION); 
		if (dialogResult == JOptionPane.NO_OPTION) {
			return;
		}

		main.getData().initialize();
		main.updateView();
		frame.repaint();
	}
	
	/**
	 * Reset data
	 * 
	 */
	private void resplitData() {
		main.getData().resplitData();
		frame.repaint();
	}

	/**
	 * Doubles and re-splits the training and test data.
	 * 
	 */
	private void growData() {
		main.getData().growData(ModelProperties.DATAPANEL_DOUBLERATE, ModelProperties.DATAPANEL_DOUBLERADIUS);
		main.updateView();
		frame.repaint();
	}

	/**
	 * Reduces and re-splits the training and test data.
	 * 
	 */
	private void reduceData() {
		main.getData().growData(1.0 / ModelProperties.DATAPANEL_DOUBLERATE, ModelProperties.DATAPANEL_DOUBLERADIUS);
		main.updateView();
		frame.repaint();
	}

	/**
	 * Set painting tool
	 * 
	 * @param tool
	 */
	private void setTool(int tool) {
		main.setDataTool(tool);
	}
}	
