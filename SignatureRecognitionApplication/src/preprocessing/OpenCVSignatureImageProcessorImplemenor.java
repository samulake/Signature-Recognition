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
		eliminateBackground();
		normalizeSize();
		eliminateBackground();
		thin();
	}

	private void eliminateBackground() {
		Mat temporaryMat = new Mat();
		double threshold = countThresholdBasedOnBrightnessGradient();
		Imgproc.threshold(this.image, temporaryMat, threshold, 255, Imgproc.THRESH_BINARY);
		temporaryMat.copyTo(this.image);
		smoothBinaryImage();
		Imgcodecs.imwrite("./testData/eliminatedBackground.jpg", this.image);
	}
	
	private void smoothBinaryImage() {
		boolean hasChange;
        int filterValue = 0;
        do {
            hasChange = false;
            for (int x = image.rows()-2; x > 0; x--) {
    			for (int y = image.cols()-2; y > 0; y--) {
    				hasChange = hasChange || smoothPoint(x, y, filterValue);
    			}
    		} 
            filterValue++;
            filterValue = filterValue % 4;
        } while (hasChange);
	}

	private boolean smoothPoint(int x, int y, int filterValue) {
		if (filterValue == 0
                && this.image.get(x, y - 1)[0] == this.image.get(x - 1, y - 1)[0]
                && this.image.get(x - 1, y - 1)[0] == this.image.get(x - 1, y)[0]
                && this.image.get(x - 1, y)[0] == this.image.get(x - 1, y + 1)[0]
                && this.image.get(x - 1, y + 1)[0] == this.image.get(x, y + 1)[0]
                && this.image.get(x, y)[0] != this.image.get(x, y - 1)[0]) {
 
            this.image.put(x, y, this.image.get(x, y - 1)[0]);
            return true;
        }
 
        if (filterValue == 1
                && this.image.get(x - 1, y)[0] == this.image.get(x - 1, y + 1)[0]
                && this.image.get(x - 1, y + 1)[0] == this.image.get(x, y + 1)[0]
                && this.image.get(x, y + 1)[0] == this.image.get(x + 1, y + 1)[0]
                && this.image.get(x + 1, y + 1)[0] == this.image.get(x + 1, y)[0]
                && this.image.get(x, y)[0] != this.image.get(x - 1, y)[0]) {
 
        	this.image.put(x, y, this.image.get(x - 1, y));
            return true;
        }
 
        if (filterValue == 2
                && this.image.get(x, y + 1)[0] == this.image.get(x + 1, y + 1)[0]
                && this.image.get(x + 1, y + 1)[0] == this.image.get(x + 1, y)[0]
                && this.image.get(x + 1, y)[0] == this.image.get(x + 1, y - 1)[0]
                && this.image.get(x + 1, y - 1)[0] == this.image.get(x, y - 1)[0]
                && this.image.get(x, y)[0] != this.image.get(x, y + 1)[0]) {
 
        	this.image.put(x, y, this.image.get(x, y + 1));
            return true;
        }
 
        if (filterValue == 3
                && this.image.get(x + 1, y)[0] == this.image.get(x + 1, y - 1)[0]
                && this.image.get(x + 1, y - 1)[0] == this.image.get(x, y - 1)[0]
                && this.image.get(x, y - 1)[0] == this.image.get(x - 1, y - 1)[0]
                && this.image.get(x - 1, y - 1)[0] == this.image.get(x - 1, y)[0]
                && this.image.get(x, y)[0] != this.image.get(x + 1, y)[0]) {
 
        	this.image.put(x, y, this.image.get(x+1, y));
            return true;
        }
 
        return false;
	}


	private double countThresholdBasedOnBrightnessGradient() {
		double threshold = 0;
		double gradientSum = 0;
		double brightnessSumMultipliedWithGradient = 0;
		for (int x = 1; x + 1 < image.rows(); x++) {
			for (int y = 1; y + 1 < image.cols(); y++) {
				double gradientX = Math.abs(image.get(x+1, y)[0])-Math.abs(image.get(x-1, y)[0]);
				double gradientY = Math.abs(image.get(x, y+1)[0]) - Math.abs(image.get(x, y-1)[0]);
				double gradient = Math.max(gradientX, gradientY);
				gradientSum += gradient;
				brightnessSumMultipliedWithGradient += image.get(x, y)[0] * gradient;
			}
		}
		threshold = brightnessSumMultipliedWithGradient / gradientSum;
		return threshold;
	}

	private void reduceNoise() {
		Mat temporaryMat = new Mat();
		/*Imgproc.GaussianBlur(this.image, temporaryMat, new Size(1,1), 3);*/
		Imgproc.bilateralFilter(this.image, temporaryMat, -1, 12, 6);
		temporaryMat.copyTo(this.image);
		this.image.convertTo(this.image, -1, 1.055, 0);
		Imgcodecs.imwrite("./testData/reducedNoise.jpg", this.image);
	}

	private void normalizeSize() {
		Mat temporaryMat = new Mat();
		Imgproc.resize(this.image, temporaryMat, new Size(300, 150));
		temporaryMat.copyTo(this.image);
		Imgcodecs.imwrite("./testData/nozmalizedSize.jpg", this.image);
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
		while (this.image.height() > 500 || this.image.width() > 500) {
			Mat temporaryMat = new Mat();		
			Imgproc.resize(this.image, temporaryMat, new Size(this.image.width()/2, this.image.height()/2));
			temporaryMat.copyTo(this.image);
		}
		Imgcodecs.imwrite("./testData/grayScale.jpg", this.image);
	}
}