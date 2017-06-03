package test;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
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
	public void getLowestBlackPixelTest() {
		OpenCVSignatureImageFeatureExtractorImplementor o = new OpenCVSignatureImageFeatureExtractorImplementor();
		Mat img;
		img = Imgcodecs.imread("./testData/testy2.png");		
		Point p = o.getLowestBlackPixel(img);
		Point ap = new Point(113, 53);
		assertEquals(ap, p);
	}

	@Test
	public void getHigestBlackPixelTest() {
		OpenCVSignatureImageFeatureExtractorImplementor o = new OpenCVSignatureImageFeatureExtractorImplementor();
		Mat img;
		img = Imgcodecs.imread("./testData/testy2.png");		
		Point p = o.getHighestBlackPixel(img);
		Point ap = new Point(17, 0);
		assertEquals(ap, p);
	}
	
	
}
