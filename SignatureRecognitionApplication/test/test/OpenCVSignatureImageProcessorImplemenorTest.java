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
		testedClass = new OpenCVSignatureImageProcessorImplemenor();
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

	//@Test
	public void testProcessImage() {
		fail("Not yet implemented");
	}
	
	@Test
	public void readImageTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method testedMethod = testedClass.getClass().getDeclaredMethod("readImage", String.class);
		testedMethod.setAccessible(true);
		testedMethod.invoke(testedClass, "./testData/signature.jpg");
		Mat image = (Mat) testedClass.getImage();
		System.out.println(image.size());
	}

}
