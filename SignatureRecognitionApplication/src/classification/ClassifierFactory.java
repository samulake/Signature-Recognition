package classification;

public interface ClassifierFactory {

	public Classifier createArtificialNeuralNetwork();

	public Classifier createDecisionTree();

	public Classifier createKNearestNeighbours();

	public Classifier createSVM();

}