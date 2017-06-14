package classification;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public class BasicClassifierFactory implements ClassifierFactory {
	private static BasicClassifierFactory singleton;
	
	private BasicClassifierFactory() {
	}
	
	@Override
	public Classifier buildDecisionTree(Instances trainData) {
		trainData.setClassIndex(trainData.numAttributes()-1);
		J48 tree = new J48();
		String[] options = new String [] {"-U", "-A", "-J"};
		try {
			tree.setOptions(options);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tree = (J48) tryToBuildClassifier(tree, trainData);
		return tree;
	}

	@Override
	public Classifier buildNeuralNetwork(Instances trainData) {
		MultilayerPerceptron multilayerPerceptron = new MultilayerPerceptron();
		trainData.setClassIndex(trainData.numAttributes()-1);

		multilayerPerceptron.setLearningRate(0.1);
		multilayerPerceptron.setMomentum(0.2);
		multilayerPerceptron.setTrainingTime(1500);
		multilayerPerceptron.setHiddenLayers("3");
		multilayerPerceptron = (MultilayerPerceptron) tryToBuildClassifier(multilayerPerceptron, trainData);
		return multilayerPerceptron;
	}

	@Override
	public Classifier buildNaiveBayesClassifier(Instances trainData) {
		Classifier bayes = new NaiveBayes();
		bayes = tryToBuildClassifier(bayes, trainData);
		return bayes;
	}
	
	private Classifier tryToBuildClassifier(Classifier classifier, Instances trainData) {
		try {
			classifier.buildClassifier(trainData);
		} catch (Exception e) {
			return null;
		}
		return classifier;
	}
	
	public static BasicClassifierFactory getInstance() {
		if(singleton == null) {
			singleton = new BasicClassifierFactory();
		}
		return singleton;
	}
}
