package analysis;

import static org.junit.Assert.*;
import static test.PrivateMethodAndField.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.*;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import application.GeneralParameters;
import weka.core.Attribute;
import weka.core.Instances;

public class AttributesDefinitionTest {
	private Method testedMethod;
	private Class<AttributesDefinition> testedClass = AttributesDefinition.class;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		testedClass = AttributesDefinition.class;
		testedMethod = null;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void attributeMapTest() throws Exception {
		Map<Attribute, AttributeSupplier> attributeMap = AttributesDefinition.attributeMap();

		assertEquals(GeneralParameters.NUMBER_OF_ATTRIBUTES, attributeMap.size());
	}

	@Test
	public void readClassDatabaseTest() throws Exception {
		testedMethod = AttributesDefinition.class.getDeclaredMethod("readClassDatabase");
		testedMethod.setAccessible(true);
		@SuppressWarnings("unchecked")
		List<String> classList = (List<String>) testedMethod.invoke(AttributesDefinition.class);

		assertNotNull(classList);
		assertFalse(classList.isEmpty());
	}

	@Test
	public void tiltPatternValuesTest() throws Exception {
		testedMethod = AttributesDefinition.class.getDeclaredMethod("tiltPatternValues");
		testedMethod.setAccessible(true);
		@SuppressWarnings("unchecked")
		List<String> resultTiltPatternList = (ArrayList<String>) testedMethod.invoke(AttributesDefinition.class);

		assertEquals(GeneralParameters.NUMBER_OF_TILT_PATTERNS, resultTiltPatternList.size());

		for (int i = 1; i <= GeneralParameters.NUMBER_OF_TILT_PATTERNS; i++) {
			assertEquals(resultTiltPatternList.get(i - 1), i + "");
		}
	}

	@Test
	public void getInstancesTest() {
		Instances result = AttributesDefinition.getInstances();
		assertEquals(GeneralParameters.NUMBER_OF_ATTRIBUTES, result.numAttributes());
		assertEquals(result.classIndex(), result.numAttributes() - 1);
		assertEquals(result.relationName(), AttributesDefinition.RELATION_NAME);
		assertTrue(result.classAttribute().name().equals(AttributesDefinition.SIGNATURE_OWNER));
	}
}
