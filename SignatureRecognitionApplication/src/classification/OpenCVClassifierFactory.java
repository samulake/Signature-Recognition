package classification;

import org.opencv.ml.ANN_MLP;
import org.opencv.ml.KNearest;
import org.opencv.ml.RTrees;
import org.opencv.ml.SVM;
import org.opencv.ml.StatModel;

public class OpenCVClassifierFactory implements ClassifierFactory {

	public void finalize() throws Throwable {

	}

	public Classifier createArtificialNeuralNetwork(){
		return new OpenCVClassifier(ANN_MLP.create());
	}

	public Classifier createDecisionTree(){
		return new OpenCVClassifier(RTrees.create());
	}

	public Classifier createKNearestNeighbours(){
		return new OpenCVClassifier(KNearest.create());
	}

	public Classifier createSVM(){
		return new OpenCVClassifier(SVM.create());
	}
}