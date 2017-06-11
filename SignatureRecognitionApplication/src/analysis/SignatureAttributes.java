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
	public static final String SIGNATURE_OWNER = "signature owner";
	
	public static final String RELATION_NAME = "signature features";
	
	public static List<Attribute> attributes() {
		List<String> signatureOwnerList = new ArrayList<String>();
			signatureOwnerList.add("Eryk Samulak nieczytelny");
			signatureOwnerList.add("Eryk Samulak czytelny");
			signatureOwnerList.add("Konrad Szałankiewicz");
			signatureOwnerList.add("Marcin Lipiński");

				
		List<Attribute> signatureAttributeList = new ArrayList<>();
		Attribute horizontalCenter = new Attribute(HORIZONTAL_CENTER);
		horizontalCenter.setWeight(10);
		Attribute verticalCenter = new Attribute(VERTICAL_CENTER);
		verticalCenter.setWeight(10);
		Attribute heightToWidthRatio = new Attribute(HEIGHT_TO_WIDTH_RATIO);
		heightToWidthRatio.setWeight(0.5);
		Attribute signatureArea = new Attribute(SIGNATURE_AREA);
		signatureArea.setWeight(0.4);
		Attribute horizontalLocalMaxima= new Attribute(HORIZONTAL_LOCAL_MAXIMA);
		horizontalLocalMaxima.setWeight(0);
		Attribute highestPixelX = new Attribute(HIGHEST_PIXEL_X);
		highestPixelX.setWeight(0.6);
		Attribute lowestPixelX = new Attribute(LOWEST_PIXEL_X);
		lowestPixelX.setWeight(0.6);

		signatureAttributeList.add(horizontalCenter);
		signatureAttributeList.add(verticalCenter);
		signatureAttributeList.add(heightToWidthRatio);
		signatureAttributeList.add(signatureArea);
		signatureAttributeList.add(horizontalLocalMaxima);
		signatureAttributeList.add(highestPixelX);
		signatureAttributeList.add(lowestPixelX);
		
		List<String> tiltPatternList = new ArrayList<String>(16);
		for(int i = 1; i <= 16; i++) {
			String patternID = "" + i;
			tiltPatternList.add(patternID);
		}

		Attribute tiltPattern = new Attribute(TILT_PATTERN, tiltPatternList);
		tiltPattern.setWeight(1);
		signatureAttributeList.add(tiltPattern);
		signatureAttributeList.add(new Attribute(SIGNATURE_OWNER, signatureOwnerList));

		return signatureAttributeList;
	}
	
	public static Instances instancesFeatures() {
		Instances instances = new Instances(RELATION_NAME,(ArrayList<Attribute>) attributes(),0);
		instances.setClassIndex(attributes().size()-1);
		return instances;
	}
}
