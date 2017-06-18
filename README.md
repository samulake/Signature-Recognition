# Signature recognition application
Group project, Warsaw University of Technology, 2016/2017

### Goal:
Comparing of the results of three classifiers: Decision Trees, Naive Bayes Classifier and Neural Network within recognition of signatures owner.

### Number of team members: 
4

### My role:
Project Manager, Programmer.

### Description:
A Java Swing application used for signature's owner recognition. The usage of the app is simple: firstly, the train data set is loaded. Only .arff format, consistent with Weka API standards, is acceptable. Secondly, an image in .png format with the signature beeing verified is loaded and displayed in GUI, preprocessed image is shown as well. Tested signature can be recognized using one of the tree classifiers: Decision Trees, Naive Bayes classifier and Neural Network. The result comprises of the signature owner ( a class ) and the probability value <0, 1>. The application works very well only for the signatures, whose samples are already in training data set. Otherwise, the chosen classifier recognize the signature's owner, that has the highest probability.
