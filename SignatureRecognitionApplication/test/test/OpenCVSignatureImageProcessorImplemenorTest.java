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

import preprocessing.OpenCVSignatureImageProcessorImplemenor;

public class OpenCVSignatureImageProcessorImplemenorTest {
	private OpenCVSignatureImageProcessorImplemenor testedClass = new OpenCVSignatureImageProcessorImplemenor();
	
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

	@Test//(timeout=1000)
	public void testProcessImage() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		readImageTest();
		reduceNoiseTest();
		eliminateBackgroundTest();
	}
	
	
	public void readImageTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method testedMethod = testedClass.getClass().getDeclaredMethod("readImage", String.class);
		testedMethod.setAccessible(true);
		testedMethod.invoke(testedClass, "./testData/image.jpg");
		Mat image = (Mat) testedClass.getImage();
		assertNotNull(image);
		assertTrue(image.channels() == 1);
		Imgcodecs.imwrite("./testData/grayImage.jpg", image);
	}
	
	public void eliminateBackgroundTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method testedMethod = testedClass.getClass().getDeclaredMethod("eliminateBackground", null);
		testedMethod.setAccessible(true);
		testedMethod.invoke(testedClass);
		Mat image = (Mat) testedClass.getImage();
		Imgcodecs.imwrite("./testData/eliminatedBackgroundImage.jpg", image);
		assertNotNull(image);
		assertTrue(image.channels() == 1);
	}
	
	public void reduceNoiseTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method testedMethod = testedClass.getClass().getDeclaredMethod("reduceNoise", null);
		testedMethod.setAccessible(true);
		testedMethod.invoke(testedClass);
		Mat image = (Mat) testedClass.getImage();
		Imgcodecs.imwrite("./testData/reducedNoiseImage.jpg", image);
		assertNotNull(image);
		assertTrue(image.channels() == 1);
	}

}
