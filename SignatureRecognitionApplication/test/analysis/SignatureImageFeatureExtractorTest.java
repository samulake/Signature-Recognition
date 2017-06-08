package analysis;

import static org.junit.Assert.*;

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
import weka.core.Instances;

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
	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void extractFeaturesTest() {
		String inputDataPathPrefix = "./testData/processImage/testImage";
		Instances instances = new Instances(SignatureAttributes.RELATION_NAME,(ArrayList<Attribute>) SignatureAttributes.attributes(), 0);
		testedClass = new SignatureImageFeatureExtractor();
		for (int testID = 0; testID < 10; testID++) {
			instances.add(testedClass.extractFeatures(inputDataPathPrefix + testID + "Result" + testID + ".png"));
		}
		
		System.out.println(instances);
	}
}
