package analysis;

import java.util.ArrayList;
import java.util.List;

import weka.core.Attribute;
import weka.core.Instances;

public class SignatureAttributes {
	public static final String HORIZONTAL_CENTER = "horizontal center";
	public static final String VERTICAL_CENTER = "vertical center";
	public static final String HEIGHT_TO_WIDTH_RATIO = "height to width ratio";
	public static final String SIGNATURE_AREA = "signature area";
	public static final String HORIZONTAL_LOCAL_MAXIMA = "horizontal local maxima";
	public static final String HIGHEST_PIXEL_X = "the highest pixel x coordinate";
	public static final String LOWEST_PIXEL_X = "the lowest pixel x coordinate";
	public static final String TILT_PATTERN = "tiltPattern";
	public static final String EDGE_POINTS = "edge points";
	
	public static final String RELATION_NAME = "signature features";
	
	public static List<Attribute> attributes() {
		List<Attribute> signatureAttributeList = new ArrayList<>();
		signatureAttributeList.add(new Attribute(HORIZONTAL_CENTER));
		signatureAttributeList.add(new Attribute(VERTICAL_CENTER));
		signatureAttributeList.add(new Attribute(HEIGHT_TO_WIDTH_RATIO));
		signatureAttributeList.add(new Attribute(SIGNATURE_AREA));
		signatureAttributeList.add(new Attribute(HORIZONTAL_LOCAL_MAXIMA));
		signatureAttributeList.add(new Attribute(HIGHEST_PIXEL_X));
		signatureAttributeList.add(new Attribute(LOWEST_PIXEL_X));
		
		List<String> tiltPatternList = new ArrayList<String>(16);
		for(int i = 1; i <= 16; i++) {
			tiltPatternList.add(new Integer(i).toString());
		}
		signatureAttributeList.add(new Attribute(TILT_PATTERN, tiltPatternList));
		signatureAttributeList.add(new Attribute(EDGE_POINTS));
		return signatureAttributeList;
	}
	
	public static Instances instancesFeatures() {
		return new Instances(RELATION_NAME,(ArrayList<Attribute>) attributes(),attributes().size());
	}
}
