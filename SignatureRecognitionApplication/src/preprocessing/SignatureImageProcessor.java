package preprocessing;

public interface SignatureImageProcessor extends ImageProcessor {
	
	public void readImage(String sourcePath);
	
	public void eliminateBackground();
	
	public void reduceNoise();
	
	public void normalizeWidth(int width);
	
	public void thin();
}