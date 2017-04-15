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
		super.processImage(sourcePath);
	}

	@Override
	public void readImage(String sourcePath) {
		super.readImage(sourcePath);
		Imgcodecs.imwrite(imagesFolderPath + "grayScale.jpg", (Mat)super.getImage());
	}

	@Override
	public void eliminateBackground() {
		super.eliminateBackground();
		Imgcodecs.imwrite(imagesFolderPath + "eliminatedBackground.jpg", (Mat)super.getImage());
	}

	@Override
	public void reduceNoise() {
		super.reduceNoise();
		Imgcodecs.imwrite(imagesFolderPath + "reducedNoise.jpg", (Mat)super.getImage());
	}

	@Override
	public void normalizeWidth(int width) {
		super.normalizeWidth(width);
		Imgcodecs.imwrite(imagesFolderPath + "nozmalizedSize.jpg", (Mat)super.getImage());
	}

	@Override
	public void thin() {
		super.thin();
		Imgcodecs.imwrite(imagesFolderPath + "thinnedImage.jpg", (Mat)super.getImage());
	}
}
