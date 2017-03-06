package preprocessing;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

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
		// TODO Auto-generated method stub

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
		this.image = Imgcodecs.imread(sourcePath);
	}

}