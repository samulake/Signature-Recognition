package analysis;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import application.GeneralParameters;
import weka.core.Attribute;

public class AttributesDefinitionTest {
	private int NUMBER_OF_ATTRIBUTES = 11;
	private final int NUMBER_OF_SAMPLE_ATTRIBUTES = 5;
	private AttributesDefinition testedClass;
	private final String ATTRIBUTE_SET_FIELD_NAME = "attributeSet";
	private Method testedPrivateMethod;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		instantiateTestedClass();
		testedPrivateMethod = null;
	}

	@After
	public void tearDown() throws Exception {
	}

	private void instantiateTestedClass() {
		testedClass = new AttributesDefinition();
	}

	@Test()
	public void testConstructor() {
		instantiateTestedClass();
	}

	@Test
	public void testGetters() {
		Set<Attribute> attributeSet = testedClass.getAttributeSet();
		assertTrue(attributeSet.size() == NUMBER_OF_ATTRIBUTES);
	}

	@Test
	public void testSetters()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		HashSet<Attribute> testAttributeSet = initializeSampleAttributeSet();
		testedClass.setAttributeSet(testAttributeSet);
		Field attributeSetField = getPrivateFieldAndSetAccessible(testedClass, ATTRIBUTE_SET_FIELD_NAME);
		assertTrue(theSameObjects(testAttributeSet, attributeSetField.get(testedClass)));
	}

	private boolean theSameObjects(Object object1, Object object2) {
		return object1 == object2;
	}

	private Field getPrivateFieldAndSetAccessible(Object classInstance, String fieldName)
			throws NoSuchFieldException, SecurityException {
		Field field = classInstance.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return field;
	}

	private Method getPrivateMethodAndSetAccessible(Object classInstance, String methodName, Class<?>... parameterTypes)
			throws NoSuchMethodException, SecurityException {
		Method method = classInstance.getClass().getDeclaredMethod(methodName, parameterTypes);
		method.setAccessible(true);
		return method;
	}

	private HashSet<Attribute> initializeSampleAttributeSet() {
		HashSet<Attribute> sampleAttributeSet = new HashSet<>();
		for (int i = 1; i <= NUMBER_OF_SAMPLE_ATTRIBUTES; i++) {
			sampleAttributeSet.add(new Attribute("sample test attribute " + i));
		}
		assertEquals(sampleAttributeSet.size(), NUMBER_OF_SAMPLE_ATTRIBUTES);
		return sampleAttributeSet;
	}

	@Test
	public void initializeAttributeSetTest() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Method testedMethod = getPrivateMethodAndSetAccessible(testedClass, "initializeAttributeSet");
		testedMethod.invoke(testedClass);
		Field field = getPrivateFieldAndSetAccessible(testedClass, ATTRIBUTE_SET_FIELD_NAME);
		@SuppressWarnings("unchecked")
		Set<Attribute> attributeSet = (Set<Attribute>) field.get(testedClass);
		assertTrue(attributeSet.size()==NUMBER_OF_ATTRIBUTES);
	}

	@Test
	public void putAttributeIntoSetTest() throws Exception {
		
		testedPrivateMethod = getPrivateMethodAndSetAccessible(testedClass, "putAttributeIntoSet", String.class, List.class);
		
		testAddingNumericValueToSet();
		testAddingNominalValueToSet();
	}
	
	private void testAddingNumericValueToSet() throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchFieldException, SecurityException {
		ArrayList<String> emptyList = new ArrayList<>();
		HashSet<Attribute> attributeSet = initializeSampleAttributeSet();
		Field attributeSetField = getPrivateFieldAndSetAccessible(testedClass, ATTRIBUTE_SET_FIELD_NAME);
		attributeSetField.set(testedClass, attributeSet);
		for (int i = 1; i <= NUMBER_OF_SAMPLE_ATTRIBUTES; i++) {
			String attributeName = "sample attribute " + i;
			testedPrivateMethod.invoke(testedClass, attributeName, emptyList);
			Attribute newlyAddedAttribute = getNewNumericAttributeIfExistsInSet(attributeSet, attributeName);
			assertTrue(newlyAddedAttribute.isNumeric());
			assertEquals(NUMBER_OF_SAMPLE_ATTRIBUTES + i, attributeSet.size());
		}
	}

	private Attribute getNewNumericAttributeIfExistsInSet(HashSet<Attribute> attributeSet, String newAttributeName) {
		Iterator<Attribute> attributeSetIterator = attributeSet.iterator();
		while(attributeSetIterator.hasNext()) {
			Attribute attribute = attributeSetIterator.next();
			if(attribute.name().equals(newAttributeName)) {
				return attribute;
			}
		}
		return null;
	}
	
	private void testAddingNominalValueToSet() throws Exception {
		List<String> sampleAttributeValuesList = Arrays.asList("nominal value 1", "nominal value 2");
		HashSet<Attribute> attributeSet = initializeSampleAttributeSet();
		Field attributeSetField = getPrivateFieldAndSetAccessible(testedClass, ATTRIBUTE_SET_FIELD_NAME);
		attributeSetField.set(testedClass, attributeSet);
		for (int i = 1; i <= NUMBER_OF_SAMPLE_ATTRIBUTES; i++) {
			String attributeName = "sample attribute " + i;
			testedPrivateMethod.invoke(testedClass, attributeName, sampleAttributeValuesList);
			Attribute newlyAddedAttribute = getNewNumericAttributeIfExistsInSet(attributeSet, attributeName);
			assertTrue(newlyAddedAttribute.isNominal());
			assertEquals(NUMBER_OF_SAMPLE_ATTRIBUTES + i, attributeSet.size());
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void readAttributeValuesTest() throws Exception {
		Method testedMethod = getPrivateMethodAndSetAccessible(testedClass, "readAttributeValues", File.class);
		
		List<String> resultList = (List<String>) testedMethod.invoke(testedClass, new File(AttributesDefinition.ATTRIBUTES_FOLDER_PATH + "tilt pattern"));
		assertEquals(resultList.size(), GeneralParameters.NUMBER_OF_TILT_PATTERNS);
		
		resultList = (List<String>) testedMethod.invoke(testedClass, new File(AttributesDefinition.ATTRIBUTES_FOLDER_PATH + "edge points"));
		assertEquals(resultList.size(), 0);
	}

}
