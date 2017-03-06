package classification;

import org.opencv.core.Mat;
import org.opencv.ml.Ml;
import org.opencv.ml.StatModel;

public class OpenCVClassifier implements Classifier<Mat> {

	private StatModel implementor;

	public OpenCVClassifier(StatModel implementor){
		this.implementor = implementor;
	}

	public void finalize() throws Throwable {

	}

	public float predict(Mat samples){
		return implementor.predict(samples);
	}

	public void train(Mat samples, Mat responses){
		implementor.train(samples, Ml.ROW_SAMPLE, responses);
	}

}