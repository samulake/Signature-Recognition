package application;

import org.opencv.core.Mat;
import org.opencv.ml.Ml;
import org.opencv.ml.StatModel;
import org.opencv.ml.TrainData;

public class OpenCVClassificationSystemFacade implements ClassificationSystemFacade<Mat> {

	private TrainData trainData;

	public void finalize() throws Throwable {

	}

	public void addTrainData(Mat samples, Mat responses){
		trainData = TrainData.create(samples, Ml.ROW_SAMPLE, responses);
	}
}