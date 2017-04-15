package preprocessing;

import org.opencv.imgcodecs.Imgcodecs;

public abstract class ImageSaverDecorator extends SignatureImageProcessor {
	private SignatureImageProcessor component;
	
	public ImageSaverDecorator(SignatureImageProcessor component) {
		this.component = component;
	}

	@Override
	public Object getImage() {
		return component.getImage();
	}

	@Override
	public void processImage(String sourcePath) {
		component.processImage(sourcePath);
	}

	@Override
	public void readImage(String sourcePath) {
		component.readImage(sourcePath);
	}

	@Override
	public void eliminateBackground() {
		component.eliminateBackground();
	}

	@Override
	public void reduceNoise() {
		component.reduceNoise();
	}

	@Override
	public void normalizeWidth(int width) {
		component.normalizeWidth(width);

	}

	@Override
	public void thin() {
		component.thin();
	}

}
