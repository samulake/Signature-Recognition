package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.opencv.core.Core;

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
	public void numberOfBlackPixelsPerRegionTest() {
		fail("Not yet implemented");
	}
	
	@Test
	public void splitMatrixTest() {
		fail("Not yet implemented");
	}
	
	@Test
	public void countBlackPixelsTest() {
		fail("Not yet implemented");
	}

}
