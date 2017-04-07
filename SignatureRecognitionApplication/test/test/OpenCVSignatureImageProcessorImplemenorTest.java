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

	//@Test //(timeout=1500)
	public void testProcessImage() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		readImageTest();
		reduceNoiseTest();
		normalizeSizeTest();
		eliminateBackgroundTest();
		thinTest();
	}
	
	@Test
	public void readImageTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		testedClass = new OpenCVSignatureImageProcessorImplemenor();
		Method testedMethod = testedClass.getClass().getDeclaredMethod("readImage", String.class);
		testedMethod.setAccessible(true);
		testedMethod.invoke(testedClass, "./testData/testImage.jpg");
		Mat image = (Mat) testedClass.getImage();
		assertNotNull(image);
		assertTrue(image.channels() == 1);
		Imgcodecs.imwrite("./testData/grayScaleImage.jpg", image);
	}
	
	@Test
	public void reduceNoiseTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Mat image = Imgcodecs.imread("./testData/grayScaleImage.jpg", Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
		testedClass = new OpenCVSignatureImageProcessorImplemenor(image);
		Method testedMethod = testedClass.getClass().getDeclaredMethod("reduceNoise", null);
		testedMethod.setAccessible(true);
		testedMethod.invoke(testedClass);
		Mat imageWithReducedNoise = (Mat) testedClass.getImage();
		assertNotNull(image);
		assertTrue(image.channels() == 1);
		Imgcodecs.imwrite("./testData/reducedNoiseImage.jpg", imageWithReducedNoise);
	}
	
	@Test
	public void eliminateBackgroundTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Mat image = Imgcodecs.imread("./testData/reducedNoiseImage.jpg", Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
		testedClass = new OpenCVSignatureImageProcessorImplemenor(image);
		Method testedMethod = testedClass.getClass().getDeclaredMethod("eliminateBackground", null);
		testedMethod.setAccessible(true);
		testedMethod.invoke(testedClass);
		Mat imageWithEliminatedBackground = (Mat) testedClass.getImage();
		assertNotNull(imageWithEliminatedBackground);
		assertTrue(imageWithEliminatedBackground.channels() == 1);
		for(int i = 0; i < imageWithEliminatedBackground.rows(); i++)
			for(int j = 0; j < imageWithEliminatedBackground.cols(); j++)
				for(double number: imageWithEliminatedBackground.get(i, j))
					assertTrue(number == 255 || number == 0);
		Imgcodecs.imwrite("./testData/eliminatedBackgroundImage.jpg", imageWithEliminatedBackground);
	}
	
	public void normalizeSizeTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method testedMethod = testedClass.getClass().getDeclaredMethod("normalizeSize", null);
		testedMethod.setAccessible(true);
		testedMethod.invoke(testedClass);
		Mat image = (Mat) testedClass.getImage();
		assertNotNull(image);
		assertTrue(image.channels() == 1);
		assertTrue(image.rows()==200 && image.cols()==400);
		Imgcodecs.imwrite("./testData/normalizedSizeImage.jpg", image);
	}
	
	public void thinTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method testedMethod = testedClass.getClass().getDeclaredMethod("thin", null);
		testedMethod.setAccessible(true);
		testedMethod.invoke(testedClass);
		Mat image = (Mat) testedClass.getImage();
		assertNotNull(image);
		assertTrue(image.channels() == 1);
		assertTrue(image.rows()==200 && image.cols()==400);
		
		Imgcodecs.imwrite("./testData/thinnedImage.jpg", image);
	}
}
