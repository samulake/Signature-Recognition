package preprocessing;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class OpenCVSignatureImageProcessorImplemenor extends SignatureImageProcessorImplementor {
	private Mat image;
	
	public OpenCVSignatureImageProcessorImplemenor() {}
	
	public OpenCVSignatureImageProcessorImplemenor(Mat image) {
		this.image = image;
	}
	
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
		Imgproc.threshold(this.image, temporaryMat, 255 * 0.85, 255, Imgproc.THRESH_BINARY);
		temporaryMat.copyTo(this.image);
	}

	private void reduceNoise() {
		Mat temporaryMat = new Mat();
		Imgproc.boxFilter(this.image, temporaryMat, -1, new Size(4, 4));
		temporaryMat.copyTo(this.image);
	}

	private void normalizeSize() {
		Mat temporaryMat = new Mat();
		Imgproc.resize(this.image, temporaryMat, new Size(400, 200));
		temporaryMat.copyTo(this.image);
	}

	private void thin() {
		boolean hasChange;
		int i = 0;
		do {
			hasChange = false;
			for (int x = 1; x + 1 < image.rows(); x++) {
				for (int y = 1; y + 1 < image.cols(); y++) {
					int numberOfWhiteToBlackTransitions = countNumberOfWhiteToBlackTransitionsAroundPixel(x, y);
					int numberOfBlackNeighbourPixels = countBlackNeighbourPixels(x, y);
					if (image.get(x, y)[0] == 0 && 2 <= numberOfBlackNeighbourPixels
							&& numberOfBlackNeighbourPixels <= 6 && numberOfWhiteToBlackTransitions == 1
							&& atLeastOneWhitePixel(image.get(x - 1, y)[0], image.get(x, y + 1)[0],
									image.get(x, y - 1)[0])
							&& atLeastOneWhitePixel(image.get(x - 1, y)[0], image.get(x, y + 1)[0],
									image.get(x + 1, y)[0])) {
						this.image.put(x, y, 255);
						hasChange = true;
					}
				}
			}
		} while (hasChange);
	}

	private boolean atLeastOneWhitePixel(double... values) {
		for (double value : values) {
			if (value == 255) {
				return true;
			}
		}
		return false;
	}

	private int countNumberOfWhiteToBlackTransitionsAroundPixel(int x, int y) {
		int counter = 0;

		if (isZeroToOneTransition(image.get(x - 1, y)[0], this.image.get(x - 1, y + 1)[0]))
			counter++;

		if (isZeroToOneTransition(this.image.get(x - 1, y + 1)[0], this.image.get(x, y + 1)[0]))
			counter++;

		if (isZeroToOneTransition(this.image.get(x, y + 1)[0], this.image.get(x + 1, y + 1)[0]))
			counter++;

		if (isZeroToOneTransition(this.image.get(x + 1, y + 1)[0], this.image.get(x + 1, y)[0]))
			counter++;

		if (isZeroToOneTransition(this.image.get(x + 1, y)[0], this.image.get(x + 1, y - 1)[0]))
			counter++;

		if (isZeroToOneTransition(this.image.get(x + 1, y - 1)[0], this.image.get(x, y - 1)[0]))
			counter++;

		if (isZeroToOneTransition(this.image.get(x, y - 1)[0], this.image.get(x - 1, y - 1)[0]))
			counter++;

		if (isZeroToOneTransition(this.image.get(x - 1, y - 1)[0], this.image.get(x - 1, y)[0]))
			counter++;

		return counter;
	}

	private boolean isZeroToOneTransition(double pixel, double nextNeighbour) {
		return pixel == 255 && nextNeighbour == 0;
	}

	private int countBlackNeighbourPixels(int x, int y) {
		int counter = 0;
		if (isBlack(this.image.get(x - 1, y)[0])) {
			counter++;
		}
		if (isBlack(this.image.get(x - 1, y + 1)[0])) {
			counter++;
		}
		if (isBlack(this.image.get(x + 1, y + 1)[0])) {
			counter++;
		}
		if (isBlack(this.image.get(x + 1, y)[0])) {
			counter++;
		}
		if (isBlack(this.image.get(x + 1, y - 1)[0])) {
			counter++;
		}
		if (isBlack(this.image.get(x, y - 1)[0])) {
			counter++;
		}
		if (isBlack(this.image.get(x - 1, y - 1)[0])) {
			counter++;
		}

		return counter;
	}

	private boolean isBlack(double pixelValue) {
		return pixelValue == 0;
	}

	private void readImage(String sourcePath) {
		this.image = Imgcodecs.imread(sourcePath, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	}
}