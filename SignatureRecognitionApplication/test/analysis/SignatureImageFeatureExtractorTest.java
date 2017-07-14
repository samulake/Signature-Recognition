package analysis;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

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
	private FeatureExtractor testedClass;
	
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
		File folderWithTestData = new File("./testData/extractFeatures");
		Instances instances = new Instances(AttributesDefinition.RELATION_NAME, new ArrayList<>(AttributesDefinition.attributeMap().keySet()), 0);
		int expectedNumberOfInstances = 10;
		File [] imagePNGArray = folderWithTestData.listFiles(file -> file.getName().endsWith(".png"));

		for(File imagePNG: imagePNGArray) {
			Instance instance = testedClass.extractFeatures(imagePNG.getAbsolutePath());
			instances.add(instance);
		}
		
		assertEquals(expectedNumberOfInstances, instances.numInstances());
		ArffSaver saver = new ArffSaver();
		saver.setInstances(instances);
		saver.setFile(new File("./testData/machineLearning/test.arff"));
		saver.writeBatch();
	}
}
