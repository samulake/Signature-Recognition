package analysis;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import weka.core.DenseInstance;
import weka.core.Instance;

import org.opencv.core.Mat;
import org.opencv.core.Point;

public class SignatureImageFeatureExtractor implements FeatureExtractor {

	public Instance extractFeatures() {
		Instance testSample = new DenseInstance(10);

		return testSample;
	}

	

}