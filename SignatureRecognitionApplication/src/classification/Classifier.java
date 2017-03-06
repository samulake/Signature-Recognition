package classification;

public interface Classifier<DataStructure> {

	public float predict(DataStructure samples);

	public void train(DataStructure samples, DataStructure responses);

}