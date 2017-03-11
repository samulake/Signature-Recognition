package preprocessing;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class OpenCVSignatureImageProcessorImplemenor extends SignatureImageProcessorImplementor {
	private Mat image;

	public void finalize() throws Throwable {
		super.finalize();
	}

	public Object getImage() {
		return this.image;
	}

	public final void processImage(String sourcePath) {
		readImage(sourcePath);
		eliminateBackground();
		reduceNoise();
		normalizeSize();
		thin();
	}

	private void eliminateBackground() {
		Mat thresholdedImage = new Mat();
		Imgproc.threshold(this.image, thresholdedImage, 255*0.75, 255, Imgproc.THRESH_BINARY);
		this.image = thresholdedImage;
	}

	private void reduceNoise() {
		// TODO Auto-generated method stub

	}

	private void normalizeSize() {
		// TODO Auto-generated method stub

	}

	private void thin() {
		// TODO Auto-generated method stub

	}

	private void readImage(String sourcePath) {
		this.image = Imgcodecs.imread(sourcePath, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	}

}