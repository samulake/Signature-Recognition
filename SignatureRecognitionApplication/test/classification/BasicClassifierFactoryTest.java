package classification;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public class BasicClassifierFactoryTest {
	private static ClassifierFactory testedClass;
	private static Instances trainData;
	private static Instances testData;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testedClass = BasicClassifierFactory.getInstance();
		trainData = readArffFile("./testData/machineLearning/train.arff");
		testData = readArffFile("./testData/machineLearning/test.arff");
	}
	
	public static Instances readArffFile(String fileName) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		Instances data = new Instances(reader);
		reader.close();
		data.setClassIndex(data.numAttributes() - 1);
		return data;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws Exception {
		assertTrue(testedClass.buildDecisionTree(trainData) instanceof J48);
		assertTrue(testedClass.buildNaiveBayesClassifier(trainData) instanceof NaiveBayes);
		assertTrue(testedClass.buildNeuralNetwork(trainData) instanceof MultilayerPerceptron);
		assertEquals(5, ((J48)testedClass.buildDecisionTree(trainData)).getOptions().length);
		Classifier classifier = testedClass.buildDecisionTree(trainData);
		for(int i = 0; i < testData.size(); i++) {
			double predictedClassID = classifier.classifyInstance(testData.instance(i));
			System.out.println(testData.classAttribute().value((int) predictedClassID));
		}
		
	}

}
