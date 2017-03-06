package analysis;

public interface SignatureImageFeatureExtractor extends ImageFeatureExtractor {

	public Object extractGlobalFeatures();

	public Object extractGridFeatures();

	public Object extractMaskFeatures();

}