package analysis;


import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;


public class SignatureImageFeatureExtractor implements FeatureExtractor {
	ArrayList<Attribute> signatureAttributeList;
	
	public SignatureImageFeatureExtractor() {
		signatureAttributeList = (ArrayList<Attribute>) SignatureAttributes.attributes();
	}

	public Instance extractFeatures(String imageFilePath) {
		Mat signatureImage = Imgcodecs.imread(imageFilePath);
		Instances dataRaw = SignatureAttributes.instancesFeatures();
		
		double [] attributesValueVector = new double [signatureAttributeList.size()];
		attributesValueVector[0] = SignatureImageUtils.getHorizontalCenter(signatureImage);
		attributesValueVector[1] = SignatureImageUtils.getVerticalCenter(signatureImage);
		attributesValueVector[2] = SignatureImageUtils.getHeightWidthRatio(signatureImage);
		attributesValueVector[3] = SignatureImageUtils.countBlackPixels(signatureImage);
		//attributesValueVector[4] = dataRaw.attribute(4).addStringValue("?");
		attributesValueVector[5] = SignatureImageUtils.getHighestBlackPixel(signatureImage).x;
		attributesValueVector[6] = SignatureImageUtils.getLowestBlackPixel(signatureImage).x;
		attributesValueVector[7] = SignatureImageUtils.getSignatureTilt(signatureImage)-1;
		

		Instance sample = new DenseInstance(1,attributesValueVector);
		System.out.println(sample);
		return sample;
	}
}