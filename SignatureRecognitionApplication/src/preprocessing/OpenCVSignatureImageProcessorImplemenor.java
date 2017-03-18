package preprocessing;

import org.opencv.core.Mat;
import org.opencv.core.Size;
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
		reduceNoise();
		normalizeSize();
		eliminateBackground();
		thin();
	}

	private void eliminateBackground() {
		Mat temporaryMat = new Mat();
		Imgproc.threshold(this.image, temporaryMat, 255*0.85, 255, Imgproc.THRESH_BINARY);
		temporaryMat.copyTo(this.image);
	}

	private void reduceNoise() {
		Mat temporaryMat = new Mat();
		Imgproc.threshold(this.image, temporaryMat, 255*0.85, 255, Imgproc.THRESH_BINARY);
		temporaryMat.copyTo(this.image);
		Imgproc.boxFilter(this.image, temporaryMat, -1, new Size(4,4));
		temporaryMat.copyTo(this.image);
	}

	private void normalizeSize() {
		Mat temporaryMat = new Mat();
		Imgproc.resize(this.image, temporaryMat, new Size(400,200));
		temporaryMat.copyTo(this.image);
	}

	private void thin() {
		// TODO Auto-generated method stub

	}

	private void readImage(String sourcePath) {
		this.image = Imgcodecs.imread(sourcePath, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	}

}