package analysis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;

public class SignatureImageFeatureExtractor implements FeatureExtractor {

	public SignatureImageFeatureExtractor() {
	}

	public Instance extractFeatures(String imageFilePath) {
		Mat signatureImage = Imgcodecs.imread(imageFilePath);
		double[] attributesValuesVector = countAttributesValues(signatureImage);
		Instance sample = new DenseInstance(1, attributesValuesVector);
		sample.setDataset(AttributesDefinition.getInstances());
		return sample;
	}

	private double[] countAttributesValues(Mat signatureImage) {
		Map<Attribute, AttributeSupplier> attributeMap = AttributesDefinition.attributeMap();
		Iterator<Attribute> attributeIterator = attributeMap.keySet().iterator();
		double[] attributesValuesVector = new double[attributeMap.size()];
		int i = 0;

		while (attributeIterator.hasNext()) {
			try {
				attributesValuesVector[i++] = attributeMap.get(attributeIterator.next()).apply(signatureImage);
			} catch (NullPointerException e) {
				attributesValuesVector[i++] = 0;
			}
		}

		return attributesValuesVector;
	}
}