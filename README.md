# Playground for Neural Networks

This program is a basic experimenter for simple neural networks. The network implementation used is SNIPE by D. Kriesel (www.dkriesel.com), however, also other network implementations can be used easily by creating corresponding wrapper classes.

The SNIPE classes are included in the source, so there are no dependencies, also this makes it possible to run the application also with Java versions lower than 7, which is the version the SNIPE jar is compiled with.

The program has been created by inspiration of http://playground.tensorflow.org which is very cool but lacks some flexibility, most of all, in this program, you can change the code like desired to do any experiment you want.

## Rough Overview

Just start the program, paint your test data with the different mouse buttons into the area on the right, click on "Train" button. You can also change learning parameters, do some stuff with the painted data (which will be automatically split randomly into training and test sample sets in ratio 1:1), change the network topology etc.

The network is using back propagation feed forward topology exclusively. Support for other topologies or training algorithms will probably be integrated in a later release.  

## Release Status

Version 0.1: The program is experimental.   
