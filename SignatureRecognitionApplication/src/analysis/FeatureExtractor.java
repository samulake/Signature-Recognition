package analysis;

import weka.core.Instance;

public interface FeatureExtractor {

	public Instance extractFeatures();

}