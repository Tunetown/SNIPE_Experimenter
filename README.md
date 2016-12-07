# Playground for Neural Networks

![MainShot](https://github.com/Tunetown/SNIPE_Experimenter/blob/master/images/MainShot.png)

This program is a GUI-driven framework for experimenting with feed forward neural networks, written in Java/Swing. It provides the possibility to integrate every Java neural network engine by implementing the NetworkWrapper class. The
implemented engines can be switched to compare the performance. Currently the following engines are implemented and integrated:

- SNIPE by David Kriesel (www.dkriesel.com), Version 0.9; The source code is included in the project, because some minor adjustments have been necessary (in particular, the NeuronBehavior interface was not serializable).
- Neuroph Version 2.92

Also, some extra activation functions have been implemented for the given engines, in particular ReLU, and TahH using the fast math library JaFaMa. 

The program has been created by inspiration of http://playground.tensorflow.org which is very cool but lacks some flexibility, most of all, being able to create any training and test data you need. Also, in this program, you can change the code like desired to do any experiment you want.

## Usage

Currently, there is no binary delivered for this program. You have to build the program yourself, either using Eclipse or anything else. The program has very few dependencies (see the *libext* folder), so this should be easily achieved for anyone who is not totally new to Java development. 

So, after building, just start the program, and paint some test data with the different mouse buttons into the area on the right, click on the "Train" button and watch the network learn. The data entered will automatically be split 1:1 into training and testing data, indicated if the data point is shown as a circle or as a square. 

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

The application has been developed using Java 1.8, and Java 1.6 in parallel, and runs with both versions. It should also work with Java 1.7, but this has not been tested.  

The repository contains a full, self-contained Eclipse project (created with Eclipse Luna). If you do not want to use Eclipse, just compile the application using the main method in class de.tunetown.nnpg.main.Main.

## Release Status

Version 0.1: The program is experimental, but functional. Development and testing has been done by myself only, so i cannot guarantee that there aren't any bugs which i did not catch. If you encounter any difficulties, please contact me.
