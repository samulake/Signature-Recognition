package analysis;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class SignatureImageFeatureExtractorTest {
	private SignatureImageFeatureExtractor testedClass;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		testedClass = new SignatureImageFeatureExtractor();
	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void extractFeaturesTest() throws Exception {
		String inputDataPathPrefix = "./testData/processImage/testImage";
		AttributesDefinition attributeDefinition = new AttributesDefinition();
		Instances instances = new Instances(SignatureImageFeatureExtractor.RELATION_NAME, new ArrayList<>(attributeDefinition.getAttributeSet()), 0);

		for (int testID = 0; testID < 10; testID++) {
			Instance testSample = testedClass.extractFeatures(inputDataPathPrefix + testID + "Result" + testID + ".png");
			instances.add(testSample);
			System.out.println(testSample);
		}

		ArffSaver saver = new ArffSaver();
		saver.setInstances(instances);
		saver.setFile(new File("./testData/machineLearning/test.arff"));
		saver.writeBatch();
	}
}
