package preprocessing;

public abstract class SignatureImageProcessor implements ImageProcessor {
	
	@Override
	public void processImage(String sourcePath) {
		readImage(sourcePath);
		eliminateBackground();
		reduceNoise();
		normalizeWidth(200);
		thin();
	}

	public abstract void readImage(String sourcePath);
	
	public abstract void eliminateBackground();
	
	public abstract void reduceNoise();
	
	public abstract void normalizeWidth(int width);
	
	public abstract void thin();
}