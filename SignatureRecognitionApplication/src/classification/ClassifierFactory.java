package classification;

import weka.classifiers.Classifier;
import weka.core.Instances;

public interface ClassifierFactory {
	public Classifier buildDecisionTree(Instances trainData);
	
	public Classifier buildNeuralNetwork(Instances trainData);
	
	public Classifier buildNaiveBayesClassifier(Instances trainData);
}
