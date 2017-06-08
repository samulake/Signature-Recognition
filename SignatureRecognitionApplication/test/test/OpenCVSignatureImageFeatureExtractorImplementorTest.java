package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import analysis.OpenCVSignatureImageFeatureExtractorImplementor;

public class OpenCVSignatureImageFeatureExtractorImplementorTest {
	private OpenCVSignatureImageFeatureExtractorImplementor testedClass = new OpenCVSignatureImageFeatureExtractorImplementor();
	
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
	public void getHeigthWidthRatioTest() {
		OpenCVSignatureImageFeatureExtractorImplementor o = new OpenCVSignatureImageFeatureExtractorImplementor();
		Mat img;
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);		
		img = Imgcodecs.imread("./testData/sign2.jpg");
		   		
		float expected = (float) 104 / (float) 195;
		float actual = o.getHeightWidthRatio(img);
		
		assertEquals(String.valueOf(expected), String.valueOf(actual));
	}
}
