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

import preprocessing.ImageSaverDecorator;
import preprocessing.OpenCVSignatureImageProcessor;
import preprocessing.SignatureImageProcessor;

public class SignatureImageProcessingTest {
	private SignatureImageProcessor testedClass;
	private final String testDataFolderPath = "./testData/";
	private final int numberOfTests = 10;
	private final String imageType = ".jpg";

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

	// @Test
	public void testFinalize() {
		fail("Not yet implemented");
	}

	// @Test
	public void testGetImage() {
		fail("Not yet implemented");
	}

	@Test // (timeout=3000)
	public void testProcessImage() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		System.out.println("Testing processImage()");
		testedClass = new ImageSaverDecorator(new OpenCVSignatureImageProcessor());
		long elapsedTime = System.currentTimeMillis();
		testedClass.processImage(testDataFolderPath + "testImage.jpg");
		System.out.println((System.currentTimeMillis() - elapsedTime) / 1000);
		Mat image = (Mat) testedClass.getImage();
		assertNotNull(image);
		assertGrayScaleImage(image);
		assertTrue(isBinaryImage(image));
		assertEquals(image.width(),200);
	}
	
	private void assertGrayScaleImage(Mat image) {
		assertEquals(image.channels(), 1);
	}
	
	private boolean isBinaryImage(Mat image) {
		for (int i = 0; i < image.rows(); i++)
			for (int j = 0; j < image.cols(); j++)
				for (double pixel : image.get(i, j))
					if (!(pixel == 255 || pixel == 0)) {
						return false;
					}
		return true;
	}

	@Test
	public void readImageTest() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		System.out.println("Testing readImage()");
		testedClass = new ImageSaverDecorator(new OpenCVSignatureImageProcessor());
		Method testedMethod = testedClass.getClass().getDeclaredMethod("readImage", String.class);
		String inputDataPathPrefix = testDataFolderPath + testedMethod.getName() + "/testImage";
		imageProcessingSubmethodTest(testedMethod, inputDataPathPrefix, imageType);
	}
	
	private void imageProcessingSubmethodTest(Method method, String inputDataPathPrefix, String inputDataSuffix) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		method.setAccessible(true);
		for (int i = 0; i < numberOfTests; i++) {
			String inputfilePath = inputDataPathPrefix + i + inputDataSuffix;
			long elapsedTime = System.currentTimeMillis();
			method.invoke(testedClass, inputfilePath);
			elapsedTime = System.currentTimeMillis() - elapsedTime;
			assertTrue(elapsedTime < 100);
			Mat resultImage = (Mat) testedClass.getImage();
			assertTrue(resultImage.channels() == 1);
			assertTrue(resultImage.width() <= 500);
			Imgcodecs.imwrite(inputDataPathPrefix + i + "Result" + i + inputDataSuffix,
					(Mat) testedClass.getImage());
		}
	}

	@Test
	public void eliminateBackgroundTest() {
		System.out.println("Testing eliminateBackground()");
	}

	@Test
	public void reduceNoiseTest() {

	}

	@Test
	public void normalizeWidthTest() {

	}

	@Test
	public void thinTest() {

	}
}
