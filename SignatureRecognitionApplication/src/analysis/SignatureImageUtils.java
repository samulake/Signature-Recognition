package analysis;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Arrays;

public class SignatureImageUtils {

    private static int blackValue = 0;
    private static final int NORMALIZED_WIDTH = 200;

    public static int[] getHorizontalHistogram(Mat input) {
        int histogram[] = new int[input.width()];
        Arrays.fill(histogram, 0);
        for (int row = 0; row < input.height(); row++) {
            for (int column = 0; column < input.width(); column++) {
                if (isBlack(input.get(row, column))) {
                    histogram[column]++;
                }
            }
        }
        return histogram;
    }

    public static int[] getVerticalHistogram(Mat input) {
        int histogram[] = new int[input.height()];
        Arrays.fill(histogram, 0);
        for (int column = 0; column < input.width(); column++) {
            for (int row = 0; row < input.height(); row++) {
                if (isBlack(input.get(row, column))) {
                    histogram[row]++;
                }
            }
        }
        return histogram;
    }

    public static int getEdgePointNumber(Mat input) {
        int edgePointNumber = 0;
        for (int rowIndex = 0; rowIndex < input.height(); rowIndex++) {
            for (int colIndex = 0; colIndex < input.width(); colIndex++) {
                if (isBlack(input.get(rowIndex, colIndex)[0]) && countBlackNeighbourPixels(rowIndex, colIndex, input) == 1) {
                    edgePointNumber++;
                }
            }
        }
        return edgePointNumber;
    }

    public static int countBlackNeighbourPixels(int row, int col, Mat image) {
        int counter = 0;
        int maxRowIndex = image.height() - 1;
        int maxColIndex = image.width() -1 ;
        if (row > 0 && isBlack(image.get(row - 1, col)[0])) {
            counter++;
        }
        if (row > 0 && col < maxColIndex && isBlack(image.get(row - 1, col + 1)[0])) {
            counter++;
        }
        if (row < maxRowIndex && col < maxColIndex && isBlack(image.get(row + 1, col + 1)[0])) {
            counter++;
        }
        if (row < maxRowIndex && isBlack(image.get(row + 1, col)[0])) {
            counter++;
        }
        if (row < maxRowIndex && col > 0 && isBlack(image.get(row + 1, col - 1)[0])) {
            counter++;
        }
        if (col > 0 && isBlack(image.get(row, col - 1)[0])) {
            counter++;
        }
        if (row > 0 && col > 0 && isBlack(image.get(row - 1, col - 1)[0])) {
            counter++;
        }
        if (col < maxColIndex && isBlack(image.get(row, col + 1)[0])) {
            counter++;
        }
        return counter;
    }

    public static boolean isBlack(double pixelValue) {
        return pixelValue == 0;
    }

    private static boolean isBlack(double[] pixel) {
        return pixel[0] == blackValue;
    }
    
    public int getSignatureTilt(Mat img) {
		int[] tilt = { 0, img.height() * img.width() };
		Mat element = Mat.zeros(10, 10, CvType.CV_8U);
		for (int i = 1; i <= 16; i++) {
			element.put(0, 0, getPattern(i));
			Mat pimg = new Mat();
			Imgproc.erode(img, pimg, element);
			if (countBlackPixels(pimg) < tilt[1]) {
				tilt[0] = i;
				tilt[1] = countBlackPixels(pimg);
			}
		}
		return tilt[0];
	}

	private double[] getPattern(int i) {

		switch (i) {
		case 1:
			return new double[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0 };
		case 2:
			return new double[] { 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0 };
		case 3:
			return new double[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0 };
		case 4:
			return new double[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0 };
		case 5:
			return new double[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 1 };
		case 6:
			return new double[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 1, 0, 0, 0 };
		case 7:
			return new double[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
					0, 0, 0, 0, 0 };
		case 8:
			return new double[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0,
					0, 0, 0, 0, 0 };
		case 9:
			return new double[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 0, 0, 0 };
		case 10:
			return new double[] { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 0, 0, 0 };
		case 11:
			return new double[] { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 0, 0, 0 };
		case 12:
			return new double[] { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 0, 0, 0 };
		case 13:
			return new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0,
					0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0,
					0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0,
					0, 0, 0, 0, 0 };
		case 14:
			return new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0,
					0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0 };
		case 15:
			return new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0,
					0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0 };
		case 16:
			return new double[] { 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0 };
		}
		return null;
	}

	public static int countBlackPixels(Mat matrix) {
		double[] pixel;
		boolean b = false;
		int numberOfBlackPixels = 0;

		for (int j = 0; j < matrix.cols(); j++) {
			for (int k = 0; k < matrix.rows(); k++) {
				pixel = matrix.get(k, j);
				b = pixel[0] == 0.0 ? true : false;
				if (b)
					numberOfBlackPixels++;
			}
		}
		return numberOfBlackPixels;
	}

	public static Point getHighestBlackPixel(Mat image) {
		double[] pixel = null;
		int i, j = 0;
		Point p = null;

		for (i = 0; i < image.height(); i++) {
			for (j = 0; j < image.width(); j++) {
				pixel = image.get(i, j);
				if (pixel[0] == 0.0f) {
					p = new Point(j, i);
					return p;
				}
			}
		}

		return p;
	}

	public static Point getLowestBlackPixel(Mat image) {
		double[] pixel = null;
		int i, j = 0;
		Point p = null;

		for (i = image.height() - 1; i >= 0; i--) {
			for (j = 0; j < image.width(); j++) {
				pixel = image.get(i, j);
				if (pixel[0] == 0.0f) {
					p = new Point(j, i);
					return p;
				}
			}
		}

		return p;
	}
	
	public static int [] numberOfBlackPixelsPerRegion (Mat image,int numberOfRows, int numberOfColumns) {
		
		ArrayList<Mat> regionList = splitMatrix(image, numberOfRows, numberOfColumns);
		int [] n = new int [regionList.size()];
		
		for ( int i = 0 ; i < regionList.size() ; i++) {
			n[i] = countBlackPixels(regionList.get(i));
		}
		return n;		
	}
	
	private static ArrayList<Mat> splitMatrix(Mat image,int numberOfRows, int numberOfColumns) {		
		int startColumn, endColumn, startRow, endRow;		
		int columnWidth = image.cols() / numberOfColumns;
		int rowHeight = image.rows() / numberOfRows;				
		ArrayList<Mat> regionList = new ArrayList<>();
				
		for (int rc = 0; rc < numberOfRows; rc++) {
			startRow = rc * rowHeight;		    
		    if ( rc == numberOfRows - 1)
				endRow = image.rows();	
		    else
		    	endRow = (rc + 1) * rowHeight;			
			for (int cc = 0; cc < numberOfColumns; cc++) {				
				startColumn = cc * columnWidth;				
				if ( cc == numberOfColumns - 1)
					endColumn = image.cols();
				else
					endColumn = (cc + 1) * columnWidth;				
				regionList.add(image.submat(startRow, endRow, startColumn, endColumn));
			}
		}
		return regionList;		
	}
}
