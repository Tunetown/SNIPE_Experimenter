package de.tunetown.nnpg.view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import de.tunetown.nnpg.main.Main;
import de.tunetown.nnpg.main.OS;

/**
 * Menu for the application
 * 
 * @author tweber
 *
 */
public class Menu extends JMenuBar implements ActionListener, ItemListener {
	private static final long serialVersionUID = 1L;

	private Main main;
	private JFrame frame;
	
	// File menu
	private JMenuItem open;
	private JMenuItem save;
	private JMenuItem quit;
	
	public Menu(Main main, JFrame frame) {
		this.main = main;
		this.frame = frame;
	}
	
	/**
	 * Initialize the menu bar
	 * 
	 */
	public void init() {
		// File menu
		JMenu file = new JMenu("File");
		add(file);
		
		open = new JMenuItem("Open Training Data");
		open.addActionListener(this);
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		file.add(open);

		save = new JMenuItem("Save Training Data");
		save.addActionListener(this);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		file.add(save);

		if (!OS.isMac()) {
			file.add(new JSeparator());
			
			// For non-Macs, add Quit command on Alt+F4
			quit = new JMenuItem("Quit");  //$NON-NLS-1$
			quit.addActionListener(this);
			quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
			file.add(quit);			
		}
		
		frame.setJMenuBar(this);
	}

	/**
	 * Action handler for all menu items
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			JMenuItem source = (JMenuItem) (e.getSource());
	
			if (source == open) {
				openDataFile();
			}
			if (source == save) {
				saveDataFile();
			}
			if (source == quit) {
				System.exit(0);
			}

		} catch (Throwable e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Save data to file
	 */
	private void saveDataFile() {
		main.stopTraining(false);
		
		JFileChooser j = new JFileChooser();
		
		int answer = j.showSaveDialog(frame);
		
		if (answer == JFileChooser.APPROVE_OPTION) {
			File file = j.getSelectedFile();
			main.getDataLoader().saveToFile(file);
		}
	}

	/**
	 * Open data file
	 */
	private void openDataFile() {
		main.stopTraining(false);
		
		JFileChooser j = new JFileChooser();
		
		int answer = j.showOpenDialog(frame);
		
		if (answer == JFileChooser.APPROVE_OPTION) {
			File file = j.getSelectedFile();
			main.getDataLoader().loadFromFile(file);

			((MainFrame)frame).getTopologyPanel().update();
			((MainFrame)frame).getTopologyPanel().resetGridSize();
			frame.repaint();
		}
	}

	/**
	 * Unused
	 * 
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {}
}
