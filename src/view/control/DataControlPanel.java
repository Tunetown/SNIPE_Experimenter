package view.control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import view.data.DataPanel;
import main.Main;

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

        // Border around control area
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
	}
	
	private void resetData() {
		main.getData().initialize();
		frame.repaint();
	}
	
	private void setTool(int tool) {
		main.setDataTool(tool);
	}

}	
