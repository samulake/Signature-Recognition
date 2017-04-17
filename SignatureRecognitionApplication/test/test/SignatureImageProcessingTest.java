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
	private final String imageExtention = ".jpg";
	Method testedMethod;
	private int maxTimeDuration;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		maxTimeDuration = 1000;
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
		testedClass.processImage(testDataFolderPath + "testImage.jpg");
		Mat image = (Mat) testedClass.getImage();
		assertTrue(isBinaryImage(image));
		assertEquals(image.width(), 200);
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

		initializeTest("readImage", String.class);
		maxTimeDuration = 100;
		for (int i = 0; i < numberOfTests; i++) {
			String inputFilePath = createInputDataPathPrefix() + i + imageExtention;
			doTesting(i, inputFilePath);
		}
	}

	private void doTesting(int testID, Object... arguments)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String inputDataPathPrefix = createInputDataPathPrefix();
		invocationTest(maxTimeDuration, arguments);
		Mat resultImage = (Mat) testedClass.getImage();
		assertCommonTests(resultImage);
		Imgcodecs.imwrite(inputDataPathPrefix + testID + "Result" + testID + imageExtention,
				resultImage);
	}
	
	private void invocationTest(long allowedElapsedTime, Object... arguments)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		long elapsedTime = invokeTestedMethodAndCountTimeDuration(arguments);
		assertTrue(elapsedTime <= allowedElapsedTime);
	}

	private long invokeTestedMethodAndCountTimeDuration(Object... arguments)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		long startTime = System.currentTimeMillis();
		testedMethod.invoke(testedClass, arguments);
		return System.currentTimeMillis() - startTime;
	}

	private String createInputDataPathPrefix() {
		return testDataFolderPath + testedMethod.getName() + "/testImage";
	}

	private void initializeTest(String testedMethodName, Class<?>... args)
			throws NoSuchMethodException, SecurityException {
		System.out.println("Testing " + testedMethodName + "()");
		testedClass = new ImageSaverDecorator(new OpenCVSignatureImageProcessor());
		testedMethod = testedClass.getClass().getDeclaredMethod(testedMethodName, args);
		testedMethod.setAccessible(true);
	}

	private void assertCommonTests(Mat image) {
		assertNotNull(image);
		assertGrayScaleImage(image);
		assertTrue(image.width() <= 500);
	}

	@Test
	public void eliminateBackgroundTest() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		initializeTest("eliminateBackground", null);
		maxTimeDuration = 400;
		for (int testID = 0; testID < numberOfTests; testID++) {
			Mat image = Imgcodecs.imread(createInputDataPathPrefix() + testID + imageExtention, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
			testedClass = new ImageSaverDecorator(new OpenCVSignatureImageProcessor(image));
			doTesting(testID);
			image = (Mat) testedClass.getImage();
			assertTrue(isBinaryImage(image));
		}
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
