package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.opencv.core.Mat;

import analysis.FeatureExtractor;
import analysis.SignatureImageFeatureExtractor;
import classification.BasicClassifierFactory;
import classification.ClassifierFactory;
import preprocessing.ImageSaverDecorator;
import preprocessing.OpenCVSignatureImageProcessor;
import preprocessing.SignatureImageProcessor;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;
import weka.core.converters.ConverterUtils.DataSource;

public class ClassificationSystemFacade {
	private Instances trainDataSet;
	private Map<String, Classifier> classifierMap;
	private String classificationResult;
	private Instance classifiedSample;
	private double predictedClassID;
	private SignatureImageProcessor imageProcessor = new ImageSaverDecorator(new OpenCVSignatureImageProcessor());
	private FeatureExtractor imageFeatureExtractor = new SignatureImageFeatureExtractor();
	public final String imagePath = "./data/testImage.png";
	public final String processedImagePath = "./data/processedImage.png";
	
	public String getImagePath() {
		return imagePath;
	}

	public String getProcessedImagePath() {
		return processedImagePath;
	}

	public ClassificationSystemFacade() {
		BasicClassifierFactory classifierFactory = BasicClassifierFactory.getInstance();
		classifierMap = new HashMap<>();
		classifierMap.put(ClassifierFactory.DECISION_TREE, classifierFactory.buildDecisionTree(null));
		classifierMap.put(ClassifierFactory.NEURAL_NETWORK, classifierFactory.buildNeuralNetwork(null));
		classifierMap.put(ClassifierFactory.NAIVE_BAYES_CLASSIFIER, classifierFactory.buildNaiveBayesClassifier(null));
		predictedClassID = -1;
		classificationResult = "";
	}

	public void classify(String dataFilePath, String classifierName) throws Exception {
		if(trainDataSet == null) {
			return;
		}
		Classifier classifier = classifierMap.get(classifierName);
		classifier.buildClassifier(trainDataSet);
		this.predictedClassID = classifier.classifyInstance(classifiedSample);
		double [] predictionProbabilites = classifier.distributionForInstance(classifiedSample);
		classificationResult = classifiedSample.classAttribute().value((int)predictedClassID) + "\n" + predictionProbabilites[(int)predictedClassID] ;
	}

	public String getClassificationResult() {
		return classificationResult;
	}

	public void loadExistingTrainDataSet(String filePath) throws IOException {
		BufferedReader fileReader;
		fileReader = new BufferedReader(new FileReader(filePath));
		this.trainDataSet = new Instances(fileReader);
		int lastAttributeIndex = this.trainDataSet.numAttributes() - 1;
		fileReader.close();
		this.trainDataSet.setClassIndex(this.trainDataSet.numAttributes() - 1);
	}

	public void reset() {
		trainDataSet = null;
		classifierMap = null;
		classifiedSample = null;
		predictedClassID = -1;
		classificationResult = "";
	}
	
	public void loadSample(String filePath) {
		imageProcessor.processImage(filePath);
		classifiedSample = imageFeatureExtractor.extractFeatures(processedImagePath);
	}

}
