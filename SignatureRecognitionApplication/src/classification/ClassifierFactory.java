package classification;

import weka.classifiers.Classifier;
import weka.core.Instances;

public interface ClassifierFactory {
	String DECISION_TREE = "Decision tree";
	String NEURAL_NETWORK = "Neural network";
	String NAIVE_BAYES_CLASSIFIER = "Naive Bayes classifier";
	
	public Classifier buildDecisionTree(Instances trainData);
	
	public Classifier buildNeuralNetwork(Instances trainData);
	
	public Classifier buildNaiveBayesClassifier(Instances trainData);
}
