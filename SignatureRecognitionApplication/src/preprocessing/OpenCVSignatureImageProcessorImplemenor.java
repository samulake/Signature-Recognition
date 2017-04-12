package preprocessing;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class OpenCVSignatureImageProcessorImplemenor extends SignatureImageProcessorImplementor {
	private Mat image;
	private Mat temporaryMat;

	public OpenCVSignatureImageProcessorImplemenor() {
		temporaryMat = new Mat();
	}

	public OpenCVSignatureImageProcessorImplemenor(Mat image) {
		this.image = image;
		temporaryMat = new Mat();
	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	public Object getImage() {
		return this.image;
	}

	public final void processImage(String sourcePath) {
		readImage(sourcePath);
		blurImage();
		eliminateBackground(countThresholdBasedOnBrightnessGradient());
		reduceNoise();
		smoothBinaryImage();
		normalizeSize(128);
		thin();
	}

	private void blurImage() {
		doBilateralFilter(-1,30, 4);
		Imgcodecs.imwrite("./testData/blurred.jpg", this.image);
	}

	private void eliminateBackground(double threshold) {
		doThresholdingBasedOnPixelBrightnessGradients();
		Imgcodecs.imwrite("./testData/eliminatedBackground.jpg", this.image);
	}

	private void smoothBinaryImage() {
		boolean hasChange;
		int filterValue = 0;
		do {
			hasChange = false;
			for (int x = image.rows() - 2; x > 0; x--) {
				for (int y = image.cols() - 2; y > 0; y--) {
					hasChange = hasChange || smoothPoint(x, y, filterValue);
				}
			}
			filterValue++;
			filterValue = filterValue % 4;
		} while (hasChange);
	}

	private boolean smoothPoint(int x, int y, int filterValue) {
		if (filterValue == 0 && this.image.get(x, y - 1)[0] == this.image.get(x - 1, y - 1)[0]
				&& this.image.get(x - 1, y - 1)[0] == this.image.get(x - 1, y)[0]
				&& this.image.get(x - 1, y)[0] == this.image.get(x - 1, y + 1)[0]
				&& this.image.get(x - 1, y + 1)[0] == this.image.get(x, y + 1)[0]
				&& this.image.get(x, y)[0] != this.image.get(x, y - 1)[0]) {

			this.image.put(x, y, this.image.get(x, y - 1)[0]);
			return true;
		}

		if (filterValue == 1 && this.image.get(x - 1, y)[0] == this.image.get(x - 1, y + 1)[0]
				&& this.image.get(x - 1, y + 1)[0] == this.image.get(x, y + 1)[0]
				&& this.image.get(x, y + 1)[0] == this.image.get(x + 1, y + 1)[0]
				&& this.image.get(x + 1, y + 1)[0] == this.image.get(x + 1, y)[0]
				&& this.image.get(x, y)[0] != this.image.get(x - 1, y)[0]) {

			this.image.put(x, y, this.image.get(x - 1, y));
			return true;
		}

		if (filterValue == 2 && this.image.get(x, y + 1)[0] == this.image.get(x + 1, y + 1)[0]
				&& this.image.get(x + 1, y + 1)[0] == this.image.get(x + 1, y)[0]
				&& this.image.get(x + 1, y)[0] == this.image.get(x + 1, y - 1)[0]
				&& this.image.get(x + 1, y - 1)[0] == this.image.get(x, y - 1)[0]
				&& this.image.get(x, y)[0] != this.image.get(x, y + 1)[0]) {

			this.image.put(x, y, this.image.get(x, y + 1));
			return true;
		}

		if (filterValue == 3 && this.image.get(x + 1, y)[0] == this.image.get(x + 1, y - 1)[0]
				&& this.image.get(x + 1, y - 1)[0] == this.image.get(x, y - 1)[0]
				&& this.image.get(x, y - 1)[0] == this.image.get(x - 1, y - 1)[0]
				&& this.image.get(x - 1, y - 1)[0] == this.image.get(x - 1, y)[0]
				&& this.image.get(x, y)[0] != this.image.get(x + 1, y)[0]) {

			this.image.put(x, y, this.image.get(x + 1, y));
			return true;
		}

		return false;
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

	private void reduceNoise() {	
		doBilateralFilter(-1, 3, 4);
		doThresholdingBasedOnPixelBrightnessGradients();
		Imgcodecs.imwrite("./testData/reducedNoise.jpg", this.image);
	}
	
	private void doBilateralFilter(int diameter, double sigmaColor, double sigmaSpace) {
		Imgproc.bilateralFilter(this.image, temporaryMat, diameter,sigmaColor, sigmaSpace);
		temporaryMat.copyTo(this.image);
	}
	
	private void doThresholdingBasedOnPixelBrightnessGradients() {
		Imgproc.threshold(this.image, temporaryMat, countThresholdBasedOnBrightnessGradient(), 256, Imgproc.THRESH_BINARY);
		temporaryMat.copyTo(this.image);
	}

	private void normalizeSize(int height) {
		int rowStart = searchFirstRowWithAtLeastOneBlackPixel()-5;
		int rowEnd = searchLastRowWithAtLeastOneBlackPixel()+5;
		int columnStart = searchFirstColumnWithAtLeastOneBlackPixel()-5;
		int columnEnd = searchLastColumnWithAtLeastOneBlackPixel()+5;

		validateSubmatParameters(rowStart, rowEnd, columnStart, columnEnd);
		
		this.image = this.image.submat(rowStart, rowEnd, columnStart, columnEnd);
		double widthToHeightRatio = (double) this.image.width() / this.image.height();
		
		Imgproc.resize(this.image, temporaryMat, new Size(height*widthToHeightRatio, height));
		temporaryMat.copyTo(this.image);
		
		Imgproc.threshold(this.image, temporaryMat, countThresholdBasedOnBrightnessGradient(), 256, Imgproc.THRESH_BINARY);
		temporaryMat.copyTo(this.image);
		
		Imgcodecs.imwrite("./testData/nozmalizedSize.jpg", this.image);
	}
	
	private void validateSubmatParameters(int rowStart, int rowEnd, int columnStart, int columnEnd) {
		if(rowStart < 0) {
			rowStart = 0;
		}
		if(rowEnd > this.image.rows()) {
			rowEnd = this.image.rows()-1;
		}
		if(columnStart < 0) {
			columnStart = 0;
		}
		if(columnEnd > this.image.cols()) {
			columnEnd = this.image.cols()-1;
		}
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

	private void thin() {
		boolean hasChange;
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

		Imgcodecs.imwrite("./testData/thinnedImage.jpg", image);
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
		reduceImageTwiceIfSizeIsOver(500, 500);
		Imgcodecs.imwrite("./testData/grayScale.jpg", this.image);
	}
	
	private void reduceImageTwiceIfSizeIsOver(int allowedMaxHeight, int allowedMaxwidth) {
		while (this.image.height() > 500 || this.image.width() > 500) {
			Imgproc.resize(this.image, temporaryMat, new Size(this.image.width() / 2, this.image.height() / 2));
			temporaryMat.copyTo(this.image);
		}
	}
	
	
}