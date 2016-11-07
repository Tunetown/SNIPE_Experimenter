package view.control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Main;

public class DataControlPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Main main;
	private JFrame frame;

	public DataControlPanel(Main main, JFrame frame) {
		this.main = main;
		this.frame = frame;
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
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
		
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
	}
	
	private void resetData() {
		main.getData().initialize();
		frame.repaint();
	}
}	
