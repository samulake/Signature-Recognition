package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.opencv.core.Mat;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;
import weka.core.converters.ConverterUtils.DataSource;

public class ClassificationSystemFacade {
	private Instances trainDataSet;
	private Map<String, Classifier> classifierMap;
	
	public void classify() {
		
	}
	
	public void loadExistingTrainDataSet(String filePath) {
		try {
			BufferedReader fileReader;
			fileReader = new BufferedReader(new FileReader(filePath));
			this.trainDataSet = new Instances(fileReader);
			int lastAttributeIndex = this.trainDataSet.numAttributes() - 1;
			fileReader.close();
			this.trainDataSet.setClassIndex(this.trainDataSet.numAttributes() - 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addSampleToTrainDataSet(String filePath) {
		
		
	}
	
	public void saveTrainingDataSet(String filePath) {
		
	}	
}
