package preprocessing;

import analysis.SignatureImageUtils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class OpenCVSignatureImageProcessor extends SignatureImageProcessor {
	private Mat image;
	private Mat temporaryMat;

	public OpenCVSignatureImageProcessor() {

	}

	public OpenCVSignatureImageProcessor(Mat image) {
		this.image = image;
	}

	public Object getImage() {
		return this.image;
	}
	
	@Override
	public void readImage(String sourcePath) {
		this.image = Imgcodecs.imread(sourcePath, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
		reduceImageTwiceIfSizeIsOver(500);
	}

	@Override
	public void eliminateBackground() {
		blurImage();
		doThresholdingBasedOnPixelBrightnessGradients();
	}

	@Override
	public void reduceNoise() {
		doBoxFilter(-1, new Size(2,2));
		doThresholdingBasedOnPixelBrightnessGradients();
	}

	@Override
	public void normalizeWidth(int width) {
		temporaryMat = new Mat();
		Size rowRange = validateRange(searchFirstRowWithAtLeastOneBlackPixel() - 5,
				searchLastRowWithAtLeastOneBlackPixel() + 5, new Size(0, this.image.height() - 1));
		Size columnRange = validateRange(searchFirstColumnWithAtLeastOneBlackPixel() - 5,
				searchLastColumnWithAtLeastOneBlackPixel() + 5, new Size(0, this.image.width() - 1));

		int rowStart = (int) rowRange.width;
		int rowEnd = (int) rowRange.height;
		int columnStart = (int) columnRange.width;
		int columnEnd = (int) columnRange.height;

		this.image = this.image.submat(rowStart, rowEnd, columnStart, columnEnd);
		resizeImageTo(width);

		Imgproc.threshold(this.image, temporaryMat, countThresholdBasedOnBrightnessGradient(), 256,
				Imgproc.THRESH_BINARY);
		temporaryMat.copyTo(this.image);
	}

	@Override
	public void thin() {
		boolean hasChange;
		do {
			hasChange = false;
			for (int x = 1; x + 1 < image.rows(); x++) {
				for (int y = 1; y + 1 < image.cols(); y++) {
					int numberOfWhiteToBlackTransitions = countNumberOfWhiteToBlackTransitionsAroundPixel(x, y);
					int numberOfBlackNeighbourPixels = SignatureImageUtils.countBlackNeighbourPixels(x, y, this.image);
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

	private void blurImage() {
		doBilateralFilter(-1, 30, 7);
	}

	private double countThresholdBasedOnBrightnessGradient() {
		double gradientSum = 0;
		double brightnessSumMultipliedWithGradient = 0;
		for (int x = 1; x + 1 < image.rows(); x++) {
			for (int y = 1; y + 1 < image.cols(); y++) {
				double gradientX = Math.abs(image.get(x + 1, y)[0]) - Math.abs(image.get(x - 1, y)[0]);
				double gradientY = Math.abs(image.get(x, y + 1)[0]) - Math.abs(image.get(x, y - 1)[0]);
				double gradient = Math.max(gradientX, gradientY);
				gradientSum += gradient;
				brightnessSumMultipliedWithGradient += image.get(x, y)[0] * gradient;
			}
		}
		return brightnessSumMultipliedWithGradient / gradientSum;
	}

	private void doBilateralFilter(int diameter, double sigmaColor, double sigmaSpace) {
		temporaryMat = new Mat();
		Imgproc.bilateralFilter(this.image, temporaryMat, diameter, sigmaColor, sigmaSpace);
		temporaryMat.copyTo(this.image);
	}
	
	private void doBoxFilter(int depth, Size kernelSize) {
		temporaryMat = new Mat();
		Imgproc.boxFilter(this.image, temporaryMat, depth, kernelSize);
		temporaryMat.copyTo(this.image);
	}

	private void doThresholdingBasedOnPixelBrightnessGradients() {
		double threshold = countThresholdBasedOnBrightnessGradient();
		Imgproc.threshold(this.image, temporaryMat, threshold, 256, Imgproc.THRESH_BINARY);
		temporaryMat.copyTo(this.image);
	}

	private Size validateRange(double start, double end, Size range) {
		if (start > end) {
			return validateRange(end, start, range);
		}
		if (start == end) {
			return new Size(range.width, range.height);
		}
		if (isOutOfRange(start, range)) {
			start = 0;
		}
		if (isOutOfRange(end, range)) {
			end = range.height;
		}
		return new Size(start, end);
	}

	private boolean isOutOfRange(double x, Size range) {
		if (x < range.width || x > range.height) {
			return true;
		} else
			return false;
	}

	private void resizeImageTo(int width) {
		temporaryMat = new Mat();
		double heightToWidthRatio = (double) this.image.height() / this.image.width();

		Imgproc.resize(this.image, temporaryMat, new Size(width, width * heightToWidthRatio));
		temporaryMat.copyTo(this.image);
	}

	private int searchFirstColumnWithAtLeastOneBlackPixel() {

		for (int y = 0; y < image.cols(); y++) {
			for (int x = 0; x < image.rows(); x++) {
				if (this.image.get(x, y)[0] == 0) {
					return y;
				}
			}
		}
		return 0;
	}

	private int searchLastColumnWithAtLeastOneBlackPixel() {
		for (int y = image.cols() - 1; y > 0; y--) {
			for (int x = image.rows() - 1; x > 0; x--) {
				if (this.image.get(x, y)[0] == 0) {
					return y;
				}
			}
		}
		return this.image.cols() - 1;
	}

	private int searchFirstRowWithAtLeastOneBlackPixel() {
		for (int x = 0; x < image.rows(); x++) {
			for (int y = 0; y < image.cols(); y++) {
				if (this.image.get(x, y)[0] == 0) {
					return x;
				}
			}
		}
		return 0;
	}

	private int searchLastRowWithAtLeastOneBlackPixel() {
		for (int x = image.rows() - 1; x > 0; x--) {
			for (int y = image.cols() - 1; y > 0; y--) {
				if (this.image.get(x, y)[0] == 0) {
					return x;
				}
			}
		}
		return this.image.rows() - 1;
	}

	private boolean atLeastOneWhitePixel(double... pixels) {
		for (double pixel : pixels) {
			if (pixel == 255) {
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

	private void reduceImageTwiceIfSizeIsOver(int allowedMaxwidth) {
		if (this.image.width() > allowedMaxwidth) {
			resizeImageTo(500);
		}
	}
}