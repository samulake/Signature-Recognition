package preprocessing;

import org.opencv.core.Mat;
import org.opencv.core.Point;
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
		Imgproc.threshold(this.image, temporaryMat, 255 * 0.85, 255, Imgproc.THRESH_BINARY);
		temporaryMat.copyTo(this.image);
	}

	private void reduceNoise() {
		Mat temporaryMat = new Mat();
		Imgproc.threshold(this.image, temporaryMat, 255 * 0.85, 255, Imgproc.THRESH_BINARY);
		temporaryMat.copyTo(this.image);
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
		do {
			hasChange = false;
			for (int x = 1; x + 1 < image.cols(); x++) {
				for (int y = 1; y + 1 < image.rows(); y++) {
					int numberOfWhiteToBlackTransitions = countNumberOfWhiteToBlackTransitionsAroundPixel(x, y);
					int numberOfBlackNeighbourPixels = countBlackNeighbourPixels(x, y);
					if (image.get(x, y)[0] == 255 && 2 <= numberOfBlackNeighbourPixels
							&& numberOfBlackNeighbourPixels <= 6 && numberOfWhiteToBlackTransitions == 1
							&& ((image.get(x - 1, y)[0] * image.get(x, y + 1)[0] * image.get(x, y - 1)[0] == 0)
									|| (countNumberOfWhiteToBlackTransitionsAroundPixel(x - 1, y) != 1))
							&& ((image.get(x - 1, y)[0] * image.get(x, y + 1)[0] * image.get(x + 1, y)[0] == 0)
									|| (countNumberOfWhiteToBlackTransitionsAroundPixel(x, y + 1) != 1))) {
						image.get(x, y)[0] = 0;
						hasChange = true;
					}
				}
			}
		} while (hasChange);
	}

	private int countNumberOfWhiteToBlackTransitionsAroundPixel(int x, int y) {
		int counter = 0;
		// p2 p3
		if (x - 1 >= 0 && y + 1 < image.cols() && image.get(x - 1, y)[0] == 0
				&& this.image.get(x - 1, y + 1)[0] == 255) {
			counter++;
		}

		// p3 p4
		if (x - 1 >= 0 && y + 1 < image.cols() && this.image.get(x - 1, y + 1)[0] == 0
				&& this.image.get(x, y + 1)[0] == 255) {
			counter++;
		}

		// p4 p5
		if (x + 1 < this.image.rows() && y + 1 < this.image.cols() && this.image.get(x, y + 1)[0] == 0
				&& this.image.get(x + 1, y + 1)[0] == 255) {
			counter++;
		}

		// p5 p6
		if (x + 1 < this.image.rows() && y + 1 < this.image.cols() && this.image.get(x + 1, y + 1)[0] == 0
				&& this.image.get(x + 1, y)[0] == 255) {
			counter++;
		}

		// p6 p7
		if (x + 1 < this.image.rows() && y - 1 >= 0 && this.image.get(x + 1, y)[0] == 0
				&& this.image.get(x + 1, y - 1)[0] == 255) {
			counter++;
		}

		// p7 p8
		if (x + 1 < this.image.rows() && y - 1 >= 0 && this.image.get(x + 1, y - 1)[0] == 0
				&& this.image.get(x, y - 1)[0] == 255) {
			counter++;
		}

		// p8 p9
		if (x - 1 >= 0 && y - 1 >= 0 && this.image.get(x, y - 1)[0] == 0 && this.image.get(x - 1, y - 1)[0] == 255) {
			counter++;
		}

		// p9 p2
		if (x - 1 >= 0 && y - 1 >= 0 && this.image.get(x - 1,y - 1)[0] == 0 && this.image.get(x - 1,y)[0] == 1) {
			counter++;
		}
		
		return counter;
	}

	private int countBlackNeighbourPixels(int x, int y) {
		return (int) (this.image.get(x - 1,y)[0] + this.image.get(x - 1,y + 1)[0] + this.image.get(x,y + 1)[0] + this.image.get(x + 1,y + 1)[0]
				+ this.image.get(x + 1,y)[0] + this.image.get(x + 1,y - 1)[0] + this.image.get(x,y - 1)[0] + this.image.get(x - 1,y - 1)[0])/255;
	}

	private void readImage(String sourcePath) {
		this.image = Imgcodecs.imread(sourcePath, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	}

}