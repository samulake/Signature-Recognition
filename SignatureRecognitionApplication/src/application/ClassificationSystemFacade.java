package application;

import classification.Classifier;

public interface ClassificationSystemFacade<DataStructure> {

	public void addTrainData(DataStructure samples, DataStructure responses);

	public float predict(Classifier classifier, DataStructure samples);

}