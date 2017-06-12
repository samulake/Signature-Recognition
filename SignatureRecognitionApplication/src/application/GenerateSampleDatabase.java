package application;

import java.io.IOException;

import org.opencv.core.Core;

import classification.ClassifierNames;
import preprocessing.OpenCVSignatureImageProcessor;
import preprocessing.SignatureImageProcessor;

public class GenerateSampleDatabase {

	public static void main(String[] args) throws Exception {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		ClassificationSystemFacade facade = new ClassificationSystemFacade();
		facade.loadExistingTrainDataSet("C:/Users/Eryk/Desktop/test.arff");
		
		for(int i = 0; i <= 49; i++) {
			String path = "C:/Users/Eryk/Desktop/1/train/testImage" + i + ".png";
			
			facade.loadSample(path);
			facade.classify(ClassifierNames.DECISION_TREE);
			System.out.println(facade.getClassifiedSample());
		}
	}

}
