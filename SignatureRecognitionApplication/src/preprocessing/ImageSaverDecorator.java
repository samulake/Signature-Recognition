package preprocessing;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class ImageSaverDecorator extends SignatureImageProcessorDecorator {
	private final String imagesFolderPath = "./data/";
	private final String testResultsFolderPath = "./testData/";
	
	public ImageSaverDecorator(SignatureImageProcessor component) {
		super(component);
	}
	
	@Override
	public Object getImage() {
		return super.getImage();
	}

	@Override
	public void processImage(String sourcePath) {
		Imgcodecs.imwrite(imagesFolderPath + "testImage.jpg", Imgcodecs.imread(sourcePath));
		super.processImage(sourcePath);
		Imgcodecs.imwrite(imagesFolderPath + "processedImage.jpg", (Mat)super.getImage());
	}

	@Override
	public void readImage(String sourcePath) {
		super.readImage(sourcePath);
		Imgcodecs.imwrite(testResultsFolderPath + "grayScale.jpg", (Mat)super.getImage());
	}

	@Override
	public void eliminateBackground() {
		super.eliminateBackground();
		Imgcodecs.imwrite(testResultsFolderPath + "eliminatedBackground.jpg", (Mat)super.getImage());
	}

	@Override
	public void reduceNoise() {
		super.reduceNoise();
		Imgcodecs.imwrite(testResultsFolderPath + "reducedNoise.jpg", (Mat)super.getImage());
	}

	@Override
	public void normalizeWidth(int width) {
		super.normalizeWidth(width);
		Imgcodecs.imwrite(testResultsFolderPath + "nozmalizedSize.jpg", (Mat)super.getImage());
	}

	@Override
	public void thin() {
		super.thin();
		Imgcodecs.imwrite(testResultsFolderPath + "thinnedImage.jpg", (Mat)super.getImage());
	}
}
