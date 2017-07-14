package analysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import application.GeneralParameters;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.ProtectedProperties;

public class AttributesDefinition {
	public static final String RELATION_NAME = "signature ownership";

	public static final String EDGE_POINTS = "edge points";
	public static final String HEIGHT_TO_WIDTH_RATIO = "height to width ratio";
	public static final String HORIZONTAL_CENTER = "horizontal center";
	public static final String VERTICAL_CENTER = "vertical center";
	public static final String HORIZONTAL_HISTOGRAM_MAX_VALUE = "horizontal histogram max value";
	public static final String VERTICAL_HISTOGRAM_MAX_VALUE = "vertical histogram max value";
	public static final String SIGNATURE_AREA = "signature area";
	public static final String HIGHEST_PIXEL = "highest pixel";
	public static final String LOWEST_PIXEL = "lowest pixel";
	public static final String TILT_PATTERN = "tilt pattern";
	public static final String SIGNATURE_OWNER = "signature owner";
	
	private AttributesDefinition() {
		
	}

	public static Map<Attribute, AttributeSupplier> attributeMap() {
		Map<Attribute, AttributeSupplier> attributeMap = new HashMap<>();
		attributeMap.put(new Attribute(EDGE_POINTS), SignatureImageUtils::getEdgePointNumber);
		attributeMap.put(new Attribute(HEIGHT_TO_WIDTH_RATIO), SignatureImageUtils::getHeightWidthRatio);
		attributeMap.put(new Attribute(HORIZONTAL_CENTER), SignatureImageUtils::getHorizontalCenter);
		attributeMap.put(new Attribute(VERTICAL_CENTER), SignatureImageUtils::getVerticalCenter);
		attributeMap.put(new Attribute(HORIZONTAL_HISTOGRAM_MAX_VALUE), SignatureImageUtils::getHorizontalHistogramMaxValue);
		attributeMap.put(new Attribute(VERTICAL_HISTOGRAM_MAX_VALUE), SignatureImageUtils::getVerticalHistogramMaxValue);
		attributeMap.put(new Attribute(SIGNATURE_AREA), SignatureImageUtils::getSignatureArea);
		attributeMap.put(new Attribute(HIGHEST_PIXEL), SignatureImageUtils::getHighestPixelXCoordinate);
		attributeMap.put(new Attribute(LOWEST_PIXEL), SignatureImageUtils::getLowestPixelXCoordinate);
		attributeMap.put(new Attribute(TILT_PATTERN, tiltPatternValues()), SignatureImageUtils::getSignatureTilt);
		attributeMap.put(new Attribute(SIGNATURE_OWNER, readClassDatabase()), new DefaultClassSupplier());
		return attributeMap;
	}

	private static List<String> readClassDatabase() {
		try {
			List<String> classValueList = new ArrayList<>();
			Scanner fileReader = new Scanner(new File("./data/Signature_Owner"));
			while (fileReader.hasNextLine()) {
				classValueList.add(fileReader.nextLine());
			}
			fileReader.close();
			return classValueList;
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		}
		return null;
	}

	private static List<String> tiltPatternValues() {
		List<String> attributeValueList = new ArrayList<>();
		for (int i = 1; i <= GeneralParameters.NUMBER_OF_TILT_PATTERNS; i++) {
			attributeValueList.add(new Integer(i).toString());
		}
		return attributeValueList;
	}

	public static Instances getInstances() {
		ArrayList<Attribute> attributeList = new ArrayList<Attribute>(attributeMap().keySet());
		Instances instances = new Instances(AttributesDefinition.RELATION_NAME, attributeList, 0);
		instances.setClassIndex(instances.numAttributes() - 1);
		return instances;
	}
}
