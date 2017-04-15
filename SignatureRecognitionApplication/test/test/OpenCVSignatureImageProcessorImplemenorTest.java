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
	private final String testDataFolderPath = "./testData/";
	private final int numberOfTests = 10;
	
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

	@Test// (timeout=3000)
	public void testProcessImage() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("Testing processImage()");
		long elapsedTime = System.currentTimeMillis();
		testedClass = new OpenCVSignatureImageProcessorImplemenor();
		testedClass.processImage(testDataFolderPath + "testImage.jpg");
		Mat image = (Mat) testedClass.getImage();
		assertNotNull(image);
		assertTrue(image.channels() == 1);
		for(int i = 0; i < image.rows(); i++)
			for(int j = 0; j < image.cols(); j++)
				for(double number: image.get(i, j))
					assertTrue(number == 255 || number == 0);
		assertTrue(image.width() == 200);
		System.out.println((System.currentTimeMillis()-elapsedTime)/1000);
	}
	
	public void blurImageTest() {
		
	}
	
	@Test
	public void readImageTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("Testing readImage()");
		testedClass = new OpenCVSignatureImageProcessorImplemenor();
		String testDataFolderPathWithImageNamePrefix = testDataFolderPath + "readImage/testImage";
		Method method = testedClass.getClass().getDeclaredMethod("readImage", String.class);
		method.setAccessible(true);
		for(int i = 0; i < numberOfTests; i++) {
			String inputfilePath = testDataFolderPathWithImageNamePrefix + i + ".jpg";
			long elapsedTime = System.currentTimeMillis();
			method.invoke(testedClass, inputfilePath);
			elapsedTime = System.currentTimeMillis()-elapsedTime;
			assertTrue(elapsedTime < 100);
			Mat resultImage = (Mat) testedClass.getImage();
			assertTrue(resultImage.channels() == 1);
			assertTrue(resultImage.width() <= 500);
			Imgcodecs.imwrite(testDataFolderPathWithImageNamePrefix + i + "Result" + i + ".jpg", (Mat) testedClass.getImage());
		}
	}
}
