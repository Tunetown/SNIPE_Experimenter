package view.topology;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import main.Main;

public class SynapsePainter {

	private final Color negativeColor = Color.green;
	private final Color positiveColor = Color.red;
	private final Color zeroColor = Color.lightGray;
	private final int maxStrokeWidth = 4;
	private final int arrowMinSize = 4;
	private final int arrowMaxSize = 8;
	
	private Main main;
	private TopologyPanel targetPanel;
	
	public SynapsePainter(Main main, TopologyPanel targetPanel) {
		this.main = main;
		this.targetPanel = targetPanel;
	}
	
	public void paint(Graphics g) {
		for(int n = 0; n<targetPanel.getNeurons().length; n++) {
			for(int n2 = 1; n2<targetPanel.getNeurons().length; n2++) {
				if (main.getNetwork().isSynapseExistent(n, n2)) {
					paintSynapse(g, n, n2);
				}
			}
		}
	}

	public void paintLegend(Graphics g, int x, int y, int w, int h) {
		for(int i=0; i<w; i++) {
			double we = (double)i/w * 2.0 - 1.0;
			g.setColor(getDataColor(zeroColor, negativeColor, positiveColor, we));
			g.drawLine(x+i, y, x+i, y+h);
		}
	}

	private void paintSynapse(Graphics g, int n1, int n2) {
		double w = main.getNetwork().getWeight(n1, n2);
		g.setColor(getDataColor(zeroColor, negativeColor, positiveColor, w));
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(getWeightThickness(w, maxStrokeWidth), BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
		
		NeuronPanel ne1 = targetPanel.getNeurons()[n1];
		NeuronPanel ne2 = targetPanel.getNeurons()[n2];
		
		double as = getWeightThickness(w, arrowMaxSize);
		if (as < arrowMinSize) as = arrowMinSize;
		drawArrow(g, ne1.getOutX(), ne1.getOutY(), ne2.getOutX(), ne2.getOutY(), as);
	}
	
	private float getWeightThickness(double weight, int max) {
		if (Double.isNaN(weight)) return 1; // TODO 
		return (float)(Math.abs(normalize(weight) * max));
	}

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
	
	private static double normalize(double weight) {
		if (weight > 1.0) return 1.0;
		if (weight < -1.0) return -1.0;
		return weight;
	}

	// TODO static not good
	public static Color getDataColor(Color colorZero, Color colorNeg, Color colorPos, double weight) {
		if (Double.isNaN(weight)) return Color.BLUE; // TODO 
		double percent;
		Color target;
		
		if(weight > 0) {
			percent = normalize(weight);
			target = colorPos;
		} else {
			percent = normalize(-weight);
			target = colorNeg;
		}
		
		double inverse_percent = 1.0 - percent;
		
		int redPart = (int)(target.getRed()*percent + colorZero.getRed()*inverse_percent);
		int greenPart = (int)(target.getGreen()*percent + colorZero.getGreen()*inverse_percent);
		int bluePart = (int)(target.getBlue()*percent + colorZero.getBlue()*inverse_percent);
		return new Color(redPart, greenPart, bluePart);
	}
}
