package test;
import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import preprocessing.OpenCVSignatureImageProcessorImplemenor;

public class OpenCVSignatureImageProcessorImplemenorTest {
	private OpenCVSignatureImageProcessorImplemenor testedClass;
	
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

	//@Test
	public void testFinalize() {
		fail("Not yet implemented");
	}

	//@Test
	public void testGetImage() {
		fail("Not yet implemented");
	}

	@Test (timeout=2500)
	public void testProcessImage() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		testedClass = new OpenCVSignatureImageProcessorImplemenor();
		testedClass.processImage("./testData/testImage.jpg");
		Mat image = (Mat) testedClass.getImage();
		assertNotNull(image);
		assertTrue(image.channels() == 1);
		for(int i = 0; i < image.rows(); i++)
			for(int j = 0; j < image.cols(); j++)
				for(double number: image.get(i, j))
					assertTrue(number == 255 || number == 0);
		assertTrue(image.rows()==200 && image.cols()==400);
		Imgcodecs.imwrite("./testData/thinnedImage.jpg", image);
	}
}
