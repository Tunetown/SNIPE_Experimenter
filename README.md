# Playground for Neural Networks

This program is a GUI-driven framework for experimenting with feed forward neural networks, written in Java/Swing. It provides the possibility to integrate every Java neural network engine by implementing the NetworkWrapper class. The
implemented engines can be switched to compare the performance. Currently the following engines are implemented and integrated:

- SNIPE by David Kriesel (www.dkriesel.com), Version 0.9; The source code is included in the project, because some minor adjustments have been necessary (in particular, the NeuronBehavior interface was not serializable).
- Neuroph Version 2.92

Also, some extra activation functions have been implemented for the given engines, in particular ReLU, and TahH using the fast math library JaFaMa. 

The program has been created by inspiration of http://playground.tensorflow.org which is very cool but lacks some flexibility, most of all, being able to create any training and test data you need. Also, in this program, you can change the code like desired to do any experiment you want.

## Usage

Just start the program, paint some test data with the different mouse buttons into the area on the right, click on the "Train" button and watch the network learn. The data entered will automatically be split 1:1 into training and testing data, indicated if the data point is shown as a circle or as a square. 

The values are color-encoded throughout the program (data points, network training visualization, synapse weights, neuron bias weights etc.). The coding is shown in a small legend, also indicating the mouse buttons to create different data values. 

You can also:

- Change learning parameters during training 
- Do some stuff with the painted data:
	- Re-Split the data: This merges all data together again and does the 1:1 split into training and test data once more, to create another distribution of the samples 
	- Grow data: This enhances the data by adding further data points randomly around the existing ones. Just try it with at least 10 data points initially.
	- Reduce data: Opposite of growing - this just removes random samples from the data.
- Change the network topology by pressing the small buttons in the topology view on the left:
	- L and l: Copy (and add) or remove the corresponding layer of neurons 
	- N and n: Add or remove one neuron from/to the corresponding layer 

The project includes a folder "examples". In the program, open the file menu and open the example files proveded to get some examples which are working pretty well.  

## Network Types

The network is using back propagation feed forward networks exclusively at the moment. Support for other topologies or training algorithms will probably be integrated in a later release.

For details about the theory behind this, see David Kriesel´s excellent book "A Brief Introduction to Neural Networks", available at http://www.dkriesel.com/science/neural_networks.

## Compatibility

The application has been developed using Java 1.8, but should also run without problems with Java 1.6. 

The repository contains a full, self-contained Eclipse project (created with Eclipse Luna). If you do not want to use Eclipse, just compile the application calling the main method in class de.tunetown.nnpg.main.Main.

## Release Status

Version 0.1: The program is experimental, and currently under development. It is NOT guaranteed that the current master is without errors and runnable.   
