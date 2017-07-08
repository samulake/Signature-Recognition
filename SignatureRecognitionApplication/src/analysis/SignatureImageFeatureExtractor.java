package analysis;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;

public class SignatureImageFeatureExtractor implements FeatureExtractor {
	private ArrayList<Attribute> signatureAttributeList;
	public static final String RELATION_NAME = "signature";

	public SignatureImageFeatureExtractor() {
		AttributesDefinition attributeDefinition = new AttributesDefinition();
		signatureAttributeList = new ArrayList<>(attributeDefinition.getAttributeSet());
	}

	public Instance extractFeatures(String imageFilePath) {
		Mat signatureImage = Imgcodecs.imread(imageFilePath);

		double[] attributesValueVector = new double[signatureAttributeList.size()];
		attributesValueVector[0] = SignatureImageUtils.getEdgePointNumber(signatureImage);
		attributesValueVector[1] = SignatureImageUtils.getHeightWidthRatio(signatureImage);
		attributesValueVector[2] = SignatureImageUtils.getHorizontalCenter(signatureImage);
		attributesValueVector[3] = SignatureImageUtils.getSignatureTilt(signatureImage)-1;
		attributesValueVector[4] = SignatureImageUtils.getVerticalCenter(signatureImage);
		attributesValueVector[5] = SignatureImageUtils.countBlackPixels(signatureImage);
		attributesValueVector[6] = SignatureImageUtils.getLowestBlackPixel(signatureImage).x;
		attributesValueVector[7] = SignatureImageUtils.getHighestBlackPixel(signatureImage).x;
		attributesValueVector[8] = SignatureImageUtils
				.getHistogramMaxValue(SignatureImageUtils.getVerticalHistogram(signatureImage));
		attributesValueVector[9] = SignatureImageUtils
				.getHistogramMaxValue(SignatureImageUtils.getHorizontalHistogram(signatureImage));

		Instance sample = new DenseInstance(1, attributesValueVector);

		return sample;
	}
}