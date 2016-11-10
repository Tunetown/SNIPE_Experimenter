package de.tunetown.nnpg.view.topology;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.tunetown.nnpg.main.Main;
import de.tunetown.nnpg.view.ViewProperties;

/**
 * Buttons for one layer in topology view
 * 
 * @author tweber
 *
 */
public class LayerButtongroup extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Main main;
	private JFrame frame;
	private TopologyPanel view;
	
	private int x;
	private int y;
	private int layer;
	
	public LayerButtongroup(Main main, TopologyPanel view, JFrame frame, int layer) {
		this.main = main;
		this.view = view;
		this.frame = frame;
		this.layer = layer;
		
		updateCoords();
		
		final LayerButtongroup wrapper = this;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				wrapper.mousePressed(e);
				wrapper.repaint();
			}
		});
	}

	protected void mousePressed(MouseEvent e) {
		if (layer == 0 || layer == main.getNetwork().countLayers() - 1) {
			addLayer();
			return;
		}
		
		if (e.getPoint().x < ViewProperties.TOPOLOGY_LAYERBUTTON_SIZE) {
			if (e.getPoint().y < ViewProperties.TOPOLOGY_LAYERBUTTON_SIZE) {
				addLayer();
			} else {
				addNeuron();
			}
		} else {
			if (e.getPoint().y < ViewProperties.TOPOLOGY_LAYERBUTTON_SIZE) {
				removeLayer();
			} else {
				removeNeuron();
			}
		}
	}

	private void removeLayer() {
		if (layer == 0 || layer == main.getNetwork().countLayers() - 1) return;
		
		if (main.getNetwork().countLayers() <= 1) return; 
		main.stopTraining(true);
		
		main.getNetwork().removeLayer(layer, true);

		view.update();
		frame.repaint();
	}

	private void addLayer() {
		main.stopTraining(true);
		
		main.getNetwork().addLayer(layer, main.getNetwork().countNeuronsInLayer(layer), true);
		
		view.update();
		view.resetGridSize();
		frame.repaint();
	}

	private void removeNeuron() {
		if (layer == 0 || layer == main.getNetwork().countLayers() - 1) return;
		
		if (main.getNetwork().countNeuronsInLayer(layer) <= 1) return; 
		main.stopTraining(true);
		
		main.getNetwork().removeNeuron(layer, false);
		
		view.update();
		frame.repaint();
	}

	private void addNeuron() {
		if (layer == 0 || layer == main.getNetwork().countLayers() - 1) return;
		
		main.stopTraining(true);
		
		main.getNetwork().addNeuron(layer, false);
		
		view.update();
		view.resetGridSize();
		frame.repaint();
	}

	/**
	 * Set the x/y values of the neuron according to grid size and the network topology
	 * 
	 */
	private void updateCoords() {
		int[] gs = view.getGridSize();
		this.x = gs[0] - ViewProperties.TOPOLOGY_LAYERBUTTON_SIZE + layer * gs[0] * 2;
		this.y = view.getHeight() - ViewProperties.TOPOLOGY_BUTTON_HEIGHT;
		this.setBounds(
				x, 
				y, 
				ViewProperties.TOPOLOGY_LAYERBUTTON_SIZE * 2, 
				ViewProperties.TOPOLOGY_LAYERBUTTON_SIZE * 2);
	}

	@Override
	public void paintComponent(Graphics g) {
		updateCoords();
		
		if (layer != 0 && layer != main.getNetwork().countLayers() - 1) {
			paintButton(g, 0, 0, "L");
			paintButton(g, ViewProperties.TOPOLOGY_LAYERBUTTON_SIZE, 0, "l");
			paintButton(g, 0, ViewProperties.TOPOLOGY_LAYERBUTTON_SIZE, "N");
			paintButton(g, ViewProperties.TOPOLOGY_LAYERBUTTON_SIZE, ViewProperties.TOPOLOGY_LAYERBUTTON_SIZE, "n");
		} else {
			paintButton(g, ViewProperties.TOPOLOGY_LAYERBUTTON_SIZE / 2, 0, "L");
		}
	}
	
	private void paintButton(Graphics g, int x, int y, String txt) {
		g.setColor(ViewProperties.TOPOLOGY_LAYERBUTTON_BGCOLOR);
		g.fillRect(x, y, ViewProperties.TOPOLOGY_LAYERBUTTON_SIZE, ViewProperties.TOPOLOGY_LAYERBUTTON_SIZE);
		g.setColor(Color.WHITE);
		g.drawRect(x, y, ViewProperties.TOPOLOGY_LAYERBUTTON_SIZE, ViewProperties.TOPOLOGY_LAYERBUTTON_SIZE);
		
		g.setFont(new Font("Sansserif", Font.PLAIN, ViewProperties.TOPOLOGY_LAYERBUTTON_SIZE - 4));
		g.setColor(ViewProperties.TOPOLOGY_LAYERBUTTON_COLOR);
		g.drawString(txt, x+4, y + ViewProperties.TOPOLOGY_LAYERBUTTON_SIZE - 3);
	}
}
