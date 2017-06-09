package preprocessing;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class ImageSaverDecorator extends SignatureImageProcessorDecorator {
	private final String imagesFolderPath = "./data/";
	
	public ImageSaverDecorator(SignatureImageProcessor component) {
		super(component);
	}
	
	@Override
	public Object getImage() {
		return super.getImage();
	}

	@Override
	public void processImage(String sourcePath) {
		Imgcodecs.imwrite(imagesFolderPath + "testImage.png", Imgcodecs.imread(sourcePath));
		super.processImage(sourcePath);
		Imgcodecs.imwrite(imagesFolderPath + "processedImage.png", (Mat)super.getImage());
	}

	@Override
	public void readImage(String sourcePath) {
		super.readImage(sourcePath);
	}

	@Override
	public void eliminateBackground() {
		super.eliminateBackground();
	}

	@Override
	public void reduceNoise() {
		super.reduceNoise();
	}

	@Override
	public void normalizeWidth(int width) {
		super.normalizeWidth(width);
	}

	@Override
	public void thin() {
		super.thin();
	}
}
