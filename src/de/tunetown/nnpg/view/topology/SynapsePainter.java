package de.tunetown.nnpg.view.topology;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import de.tunetown.nnpg.main.Main;
import de.tunetown.nnpg.view.ViewProperties;

/**
 * Helper class which paints the network synapses between the neurons in the topology.
 * 
 * @author xwebert
 *
 */
public class SynapsePainter {

	private Main main;
	private TopologyPanel targetPanel;
	
	private ViewProperties properties = new ViewProperties();
	
	public SynapsePainter(Main main, TopologyPanel targetPanel) {
		this.main = main;
		this.targetPanel = targetPanel;
	}
	
	public void paint(Graphics g) {
		synchronized (main.getNetworkLock()) {
			for(int n = 0; n<targetPanel.getNeurons().length; n++) {
				for(int n2 = 1; n2<targetPanel.getNeurons().length; n2++) {
					if (main.getNetwork().isSynapseExistent(n, n2)) {
						paintSynapse(g, n, n2);
					}
				}
			}
		}
	}

	/**
	 * This paints a color legend.
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public void paintLegend(Graphics g, int x, int y, int w, int h) {
		g.setFont(new Font("Sansserif", Font.PLAIN, 10));
		g.setColor(Color.BLACK);
		g.drawString("-1", x, y + 25);
		g.drawString("0", x + w/2-2, y + 25);
		g.drawString("1", x + w-5, y + 25);

		for(int i=0; i<w; i++) {
			double we = (double)i/w * 2.0 - 1.0;
			g.setColor(properties.getDataColor(we));
			g.drawLine(x+i, y, x+i, y+h);
		}
	}

	/**
	 * Paint one synapse from neuron n1 to neuron n2.
	 * 
	 * @param g
	 * @param n1
	 * @param n2
	 */
	private void paintSynapse(Graphics g, int n1, int n2) {
		double w = main.getNetwork().getWeight(n1, n2);
		g.setColor(properties.getDataColor(w));
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(getWeightThickness(w, ViewProperties.TOPOLOGY_MAX_SYNAPSE_WIDTH), BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
		
		NeuronPanel ne1 = targetPanel.getNeurons()[n1];
		NeuronPanel ne2 = targetPanel.getNeurons()[n2];
		
		double as = getWeightThickness(w, ViewProperties.TOPOLOGY_ARROW_MAX_SIZE);
		if (as < ViewProperties.TOPOLOGY_ARROW_MIN_SIZE) as = ViewProperties.TOPOLOGY_ARROW_MIN_SIZE;
		drawArrow(g, ne1.getOutX(), ne1.getOutY(), ne2.getOutX(), ne2.getOutY(), as);
	}
	
	/**
	 * Returns the thickness for a synapse
	 * 
	 * @param weight
	 * @param max
	 * @return
	 */
	private float getWeightThickness(double weight, int max) {
		if (Double.isNaN(weight)) return 1; 
		return (float)(Math.abs(properties.normalize(weight) * max));
	}

	/**
	 * Draws an arrowed line
	 * 
	 * @param g1
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param size
	 */
	private void drawArrow(Graphics g1, int x1, int y1, int x2, int y2, double size) {
        Graphics2D g = (Graphics2D) g1.create();

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx*dx + dy*dy);
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);

        // Draw horizontal arrow starting in (0, 0)
        g.drawLine(0, 0, len, 0);
        len *= 0.8;
        int sizeInt = (int)size;
        g.fillPolygon(new int[] {len, len-sizeInt, len-sizeInt, len},
                      new int[] {0, -sizeInt, sizeInt, 0}, 4);
    }
}
