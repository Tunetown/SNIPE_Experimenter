package com.dkriesel.snipe.util;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.dkriesel.snipe.core.NeuralNetwork;

/**
 * Experimental tool that returns some designed code for the GraphViz DOT Engine
 * (see graphviz.org) to visualize the neural net. All color getter and setters
 * receive and return Strings in which colors are encoded in hexadecimal way,
 * for instance "FF0000" for red.
 * 
 * The weaklabelsupresstheshold defines the lower bound of synapse weight
 * absolutes that are given labels. SupressWeakSynapses defines whether or not
 * not the labels are completely suppressed. ShowSynapseColorGradient defines if
 * synapses are to assign gradient colors corresponding to their synaptic values
 * generated from their weightgradientcolors. If tryToShortenStrongSynapses is
 * set, some edge weight is set in the DOT-Code that makes strong synapses
 * shorter.
 * 
 * Also please note that, contrary to the implementation, you will find one bias
 * neuron per layer in visualization in order to prevent one Neuron being
 * connected to all others in the dot code.
 * 
 * @author David Kriesel / dkriesel.com
 * 
 */
public class GraphVizEncoder {

	// neural net graphViz output parameters
	private double weakLabelSupressThreshold = 0.1;
	private boolean suppressWeakSynapses = true;
	private boolean showSynapseColorGradient = false;
	private boolean printWeightValues = true;
	private boolean tryToShortenStrongSynapses = true;
	private String innerNeuronFillColor = "FFFFFF";

	/**
	 * @return the weakLabelSupressThreshold
	 */
	public double getWeakLabelSupressThreshold() {
		return weakLabelSupressThreshold;
	}

	/**
	 * @param weakLabelSupressThreshold
	 *            the weakLabelSupressThreshold to set
	 */
	public void setWeakLabelSupressThreshold(double weakLabelSupressThreshold) {
		this.weakLabelSupressThreshold = weakLabelSupressThreshold;
	}

	/**
	 * @return the suppressWeakSynapses
	 */
	public boolean isSuppressWeakSynapses() {
		return suppressWeakSynapses;
	}

	/**
	 * @param suppressWeakSynapses
	 *            the suppressWeakSynapses to set
	 */
	public void setSuppressWeakSynapses(boolean suppressWeakSynapses) {
		this.suppressWeakSynapses = suppressWeakSynapses;
	}

	/**
	 * @return the showSynapseColorGradient
	 */
	public boolean isShowSynapseColorGradient() {
		return showSynapseColorGradient;
	}

	/**
	 * @param showSynapseColorGradient
	 *            the showSynapseColorGradient to set
	 */
	public void setShowSynapseColorGradient(boolean showSynapseColorGradient) {
		this.showSynapseColorGradient = showSynapseColorGradient;
	}

	/**
	 * @return the printWeightValues
	 */
	public boolean isPrintWeightValues() {
		return printWeightValues;
	}

	/**
	 * @param printWeightValues
	 *            the printWeightValues to set
	 */
	public void setPrintWeightValues(boolean printWeightValues) {
		this.printWeightValues = printWeightValues;
	}

	/**
	 * @return the tryToShortenStrongSynapses
	 */
	public boolean isTryToShortenStrongSynapses() {
		return tryToShortenStrongSynapses;
	}

	/**
	 * @param tryToShortenStrongSynapses
	 *            the tryToShortenStrongSynapses to set
	 */
	public void setTryToShortenStrongSynapses(boolean tryToShortenStrongSynapses) {
		this.tryToShortenStrongSynapses = tryToShortenStrongSynapses;
	}

	/**
	 * @return the innerNeuronFillColor
	 */
	public String getInnerNeuronFillColor() {
		return innerNeuronFillColor;
	}

	/**
	 * @param innerNeuronFillColor
	 *            the innerNeuronFillColor to set
	 */
	public void setInnerNeuronFillColor(String innerNeuronFillColor) {
		this.innerNeuronFillColor = innerNeuronFillColor;
	}

	/**
	 * @return the inputNeuronFillColor
	 */
	public String getInputNeuronFillColor() {
		return inputNeuronFillColor;
	}

	/**
	 * @param inputNeuronFillColor
	 *            the inputNeuronFillColor to set
	 */
	public void setInputNeuronFillColor(String inputNeuronFillColor) {
		this.inputNeuronFillColor = inputNeuronFillColor;
	}

	/**
	 * @return the outputNeuronFillColor
	 */
	public String getOutputNeuronFillColor() {
		return outputNeuronFillColor;
	}

	/**
	 * @param outputNeuronFillColor
	 *            the outputNeuronFillColor to set
	 */
	public void setOutputNeuronFillColor(String outputNeuronFillColor) {
		this.outputNeuronFillColor = outputNeuronFillColor;
	}

	/**
	 * @return the innerNeuronTextColor
	 */
	public String getInnerNeuronTextColor() {
		return innerNeuronTextColor;
	}

	/**
	 * @param innerNeuronTextColor
	 *            the innerNeuronTextColor to set
	 */
	public void setInnerNeuronTextColor(String innerNeuronTextColor) {
		this.innerNeuronTextColor = innerNeuronTextColor;
	}

	/**
	 * @return the graphInputNeuronTextColor
	 */
	public String getGraphInputNeuronTextColor() {
		return graphInputNeuronTextColor;
	}

	/**
	 * @param graphInputNeuronTextColor
	 *            the graphInputNeuronTextColor to set
	 */
	public void setGraphInputNeuronTextColor(String graphInputNeuronTextColor) {
		this.graphInputNeuronTextColor = graphInputNeuronTextColor;
	}

	/**
	 * @return the outputNeuronTextColor
	 */
	public String getOutputNeuronTextColor() {
		return outputNeuronTextColor;
	}

	/**
	 * @param outputNeuronTextColor
	 *            the outputNeuronTextColor to set
	 */
	public void setOutputNeuronTextColor(String outputNeuronTextColor) {
		this.outputNeuronTextColor = outputNeuronTextColor;
	}

	/**
	 * @return the weightValueColor
	 */
	public String getWeightValueColor() {
		return weightValueColor;
	}

	/**
	 * @param weightValueColor
	 *            the weightValueColor to set
	 */
	public void setWeightValueColor(String weightValueColor) {
		this.weightValueColor = weightValueColor;
	}

	/**
	 * @return the neuronBorderColor
	 */
	public String getNeuronBorderColor() {
		return neuronBorderColor;
	}

	/**
	 * @param neuronBorderColor
	 *            the neuronBorderColor to set
	 */
	public void setNeuronBorderColor(String neuronBorderColor) {
		this.neuronBorderColor = neuronBorderColor;
	}

	/**
	 * @return the edgeColor
	 */
	public String getEdgeColor() {
		return edgeColor;
	}

	/**
	 * @param edgeColor
	 *            the edgeColor to set
	 */
	public void setEdgeColor(String edgeColor) {
		this.edgeColor = edgeColor;
	}

	/**
	 * @return the weakEdgeColor
	 */
	public String getWeakEdgeColor() {
		return weakEdgeColor;
	}

	/**
	 * @param weakEdgeColor
	 *            the weakEdgeColor to set
	 */
	public void setWeakEdgeColor(String weakEdgeColor) {
		this.weakEdgeColor = weakEdgeColor;
	}

	/**
	 * @return the weightGradientMinColor
	 */
	public String getWeightGradientMinColor() {
		return weightGradientMinColor;
	}

	/**
	 * @param weightGradientMinColor
	 *            the weightGradientMinColor to set
	 */
	public void setWeightGradientMinColor(String weightGradientMinColor) {
		this.weightGradientMinColor = weightGradientMinColor;
	}

	/**
	 * @return the weightGradientMaxColor
	 */
	public String getWeightGradientMaxColor() {
		return weightGradientMaxColor;
	}

	/**
	 * @param weightGradientMaxColor
	 *            the weightGradientMaxColor to set
	 */
	public void setWeightGradientMaxColor(String weightGradientMaxColor) {
		this.weightGradientMaxColor = weightGradientMaxColor;
	}

	private String inputNeuronFillColor = "DAE5F7";
	private String outputNeuronFillColor = "7284A1";
	private String innerNeuronTextColor = "000000";
	private String graphInputNeuronTextColor = "000000";
	private String outputNeuronTextColor = "FFFFFF";
	private String weightValueColor = "000000";
	private String neuronBorderColor = "435470";
	private String edgeColor = "444444";
	private String weakEdgeColor = "AAAAAA";
	private String weightGradientMinColor = "FF0000";
	private String weightGradientMaxColor = "00FF00";

	/**
	 * Returns some designed code for the GraphViz DOT Engine (see graphviz.org)
	 * to visualize the neural net.
	 * 
	 * NOTE: This method contains a lot of getweight(), issynapseexistent() and
	 * issynapseswitchedon() invocations. Thus, it is not optimal in calculating
	 * complexity.
	 * 
	 * Also, large networks may get expensive to render in GraphViz, even though
	 * the DOT code is easy to create.
	 * 
	 * @param net
	 *            the neural network to print out
	 * @param digraphName
	 *            a name for the DOT digraph definition
	 * 
	 * @return The GraphViz DOT Code.
	 */
	@SuppressWarnings("boxing")
	public String getGraphVizCode(NeuralNetwork net, String digraphName) {

		DecimalFormat normalWeightFormat = new DecimalFormat(
				"################.#");
		StringBuilder code = new StringBuilder();
		code.append("digraph " + digraphName + "{\n");
		code.append("/* Code generated by dkriesel.com SNIPE */\n");
		code.append("/* http://www.dkriesel.com/en/science/snipe */\n");
		code.append("/*Execute with: dot inputfilename -Tpng -o outputfilename*/\n");
		code.append("/*just ignore warning that BIAS node is too small for label*/\n");
		code.append("nodesep=0.1\n");
		code.append("\n/* define style of inner neurons and synapses */\n");
		code.append("node [fixedsize=true, fontname=Arial, fontsize=11, fontcolor=\"#"
				+ innerNeuronTextColor
				+ "\", shape=circle, fillcolor=\"#"
				+ innerNeuronFillColor
				+ "\", style=filled, color=\"#"
				+ neuronBorderColor + "\"]\n");
		code.append("edge [color=\"#"
				+ edgeColor
				+ "\",arrowhead=vee,labelfloat=false, fontname=Arial, fontsize=8, fontcolor=\"#"
				+ weightValueColor + "\"]\n");
		code.append("\n/* define style of input neurons */\n");
		for (int i = 0; i < net.getDescriptor().countInputNeurons(); i++) {
			code.append((i + 1)
					+ " [fixedsize=true, fontname=Arial, fontcolor=\"#"
					+ graphInputNeuronTextColor
					+ "\", fontsize=11, shape=circle, fillcolor=\"#"
					+ inputNeuronFillColor + "\", style=filled, color=\"#"
					+ neuronBorderColor + "\"]\n");
		}

		code.append("\n/* define style of output neurons */\n");
		for (int i = net.countNeurons(); i >= net.getNeuronFirstInLayer(net
				.countLayers() - 1); i--) {
			code.append(i
					+ " [fixedsize=true, fontname=Arial, fontsize=11, fontcolor=\"#"
					+ outputNeuronTextColor + "\", shape=circle, fillcolor=\"#"
					+ outputNeuronFillColor + "\", style=filled, color=\"#"
					+ neuronBorderColor + "\"]\n");
		}

		code.append("\n/* define style of bias neurons */\n");
		for (int i = 1; i <= net.countLayers() - 1; i++) {
			code.append("bias"
					+ i
					+ " [label=\"BIAS\", fixedsize=true, fontname=Arial,fontsize=11, shape=circle, fillcolor=\"#"
					+ innerNeuronFillColor + "\", style=filled, color=\"#"
					+ neuronBorderColor + "\"]\n");
		}

		code.append("\n/* Define Neural Network Structure */\n");
		code.append("/* Attention: Synapse weight labels with weight absolute <= "
				+ weakLabelSupressThreshold + " are suppressed. */\n");
		code.append("/* Those weak synapses are then painted in another color.*/\n");
		double maxWeight = 0.0;
		double minWeight = 0.0;

		// if color gradient wanted, define max and min weights
		if (showSynapseColorGradient) {
			for (int i = 0; i < net.countNeurons() + 1; i++) {
				for (int j = 0; j < net.countNeurons() + 1; j++) {
					if (net.isSynapseExistent(i, j)) {
						if (net.getWeight(i, j) > maxWeight) {
							maxWeight = net.getWeight(i, j);
						}
						if (net.getWeight(i, j) < minWeight) {
							minWeight = net.getWeight(i, j);
						}
					}
				}
			}
		}
		// define Synapses and Parameters
		for (int i = 0; i < net.countNeurons() + 1; i++) {
			for (int j = 0; j < net.countNeurons() + 1; j++) {
				if (net.isSynapseExistent(i, j)) {
					ArrayList<String> parameters = new ArrayList<String>();
					// add synapse. If synapse comes from Bias, map start to
					// biasX, where X is the goal layer of the synapse.
					code.append("\t"
							+ (i == 0 ? ("bias" + net.getLayerOfNeuron(j)) : i)
							+ " -> " + j);

					// is synapse weak?
					boolean synapseWeak = (Math.abs(net.getWeight(i, j)) <= weakLabelSupressThreshold);

					// determine color
					if (showSynapseColorGradient) {
						String colorparam = "color=\"#FFFFFF\"";
						Color c = generateColorInRange(
								getColorFromHex(weightGradientMinColor),
								getColorFromHex("FFFFFF"),
								getColorFromHex(weightGradientMaxColor),
								minWeight, 0.0, maxWeight, net.getWeight(i, j));
						colorparam = "color=\"#" + getHexFromColor(c) + "\"";

						parameters.add(colorparam);
					} else {
						if (suppressWeakSynapses && synapseWeak) {
							parameters.add("color=\"#" + weakEdgeColor + "\"");
						}
					}

					// determine label
					if (printWeightValues) {
						if (!(synapseWeak && suppressWeakSynapses)) {
							if (Math.abs(net.getWeight(i, j)) < 10.0) {
								parameters.add("label=\""
										+ normalWeightFormat.format(net
												.getWeight(i, j)) + "\"");
							} else {
								// Large Weights will be shortened
								parameters.add("label=\""
										+ (int) net.getWeight(i, j) + "\"");

							}
						}
					}

					// determine edgeWeight
					int edgeWeight = 100;
					if (tryToShortenStrongSynapses) {
						if (Math.abs(net.getWeight(i, j) * 2.0) < 1.0) {
							edgeWeight = 0;
						} else {
							edgeWeight = (int) (Math.log(Math.abs(net
									.getWeight(i, j)) * 2.0));
						}
					} else {
						if (suppressWeakSynapses && synapseWeak) {
							// weak synapse
							edgeWeight = 1;
						}
					}

					if (i == 0) {
						// edge comes from bias
						edgeWeight = 1;
					}

					parameters.add("weight=" + edgeWeight);

					// add parameters
					if (parameters.size() > 0) {
						code.append(" [");
						for (int k = 0; k < parameters.size(); k++) {
							code.append(parameters.get(k));
							if (k != parameters.size() - 1) {
								code.append(", ");
							}
						}
						code.append("]\n");
					}
				}
			}
		}
		code.append("}");

		return code.toString();
	}

	private static Color generateColorInRange(Color lowColor, Color middleColor,
			Color highColor, double low, double middle, double high,
			double value) {

		if (value <= low) {
			return lowColor;
		}
		if (Math.abs(value - middle) < 0.01) {
			return middleColor;
		}
		if (value >= high) {
			return highColor;
		}

		if (value > low && value < middle) {
			double diff = middle - low;
			double offset = value - low;
			double ratio = offset / diff;
			double ratio2 = 1.0 - ratio;

			double r = lowColor.getRed() * ratio2 + middleColor.getRed()
					* ratio;
			double g = lowColor.getGreen() * ratio2 + middleColor.getGreen()
					* ratio;
			double b = lowColor.getBlue() * ratio2 + middleColor.getBlue()
					* ratio;

			return new Color((int) r, (int) g, (int) b);
		}
		if (value > middle && value < high) {
			double diff = high - middle;
			double offset = value - middle;
			double ratio = offset / diff;
			double ratio2 = 1.0 - ratio;

			double r = middleColor.getRed() * ratio2 + highColor.getRed()
					* ratio;
			double g = middleColor.getGreen() * ratio2 + highColor.getGreen()
					* ratio;
			double b = middleColor.getBlue() * ratio2 + highColor.getBlue()
					* ratio;

			return new Color((int) r, (int) g, (int) b);
		}

		// this should never happen
		return null;

	}

	private static Color getColorFromHex(String s) {
		int intValue = Integer.parseInt(s, 16);
		return new Color(intValue);
	}

	private static String getHexFromColor(Color c) {
		// Color to hex
		String s = Integer.toHexString(c.getRGB());
		s = s.substring(2);
		return s;
	}

}
