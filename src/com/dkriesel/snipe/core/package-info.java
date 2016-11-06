/**
 * Contains the two core classes of SNIPE - NeuralNetworkDescriptor, which you use to outline and create instances of Neural Networks, and the NeuralNetwork class itself; <b>Both of those classes' documentations, and the package documentation as well contain fundamental information to get used to SNIPE</b>.
 * 
 * <h1> SNIPE's General Features</h1>
 * 
 * SNIPE was originally designed for high performance simulations with lots and lots of even large neural networks being trained simultaneously. Later, I decided to give it away as a professional reference implementation that covers the network aspects handled with in my manuscript "A Brief Introduction to Neural Networks", while being faster and more efficient than lots of other implementations due to the original high-performance simulation design goal. SNIPE is designed with respect to each of the following goals:
 * <ol>
 * <li><b>Generalized data structure for arbitrary network topologies</b>, so that virtually all network structures
 * can be realized or even easily hand-crafted. 
 * <li><b>Built-In, fast and easy-to-use learning operators</b> for gradient descent or evolutionary 
 * learning, as well as mechanisms for efficient control of even large network populations.
 * <li><b>Mechanisms for design and control of even large populations of neural
 * networks</b>
 * <li><b>Optimal speed neural network data propagation</b> in contrast to naive data
 * structures, even in special cases like multilayer perceptrons and sparse
 * networks, as well as low computational topology editing effort.
 * <li><b>Low memory consumption</b> - grows only with the number of existing synapses, not
 * quadratically with the number of neurons
 * <li><b>In-situ processing</b> - no extra memory or preprocessing of the data structure is necessary
 * in order to use the network after editing
 * <li><b>Usage of only low level data structures (arrays)</b> for easy portability. It
 * is not the goal to quench the last tiniest bit of asymptotical complexity out
 * of the structure, but to make it usable, light weight and fast in praxis.
 * <li><b>No object-oriented overload</b>, like objects for every neuron or even
 * synapses, etc.
 * </ol>   
 * 
 * <h1> Getting Started </h1>
 * 
 * <ol>
 * <li>Read this doc (almost done!)
 * <li>Read doc of NeuralNetworkDescriptor to learn the very basics
 * <li>Read as much as you wish of the doc of NeuralNetwork to get the details and an idea of the features and their computational efforts
 * <li>Have a look at the code examples
 * </ol>
 */
package com.dkriesel.snipe.core;

