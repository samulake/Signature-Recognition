package analysis;

import org.opencv.core.Mat;

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

    public static int getHorizontalCenter(Mat input){

        int[] horizontalHistogram = getHorizontalHistogram(input);
        int weight = 0;
        int sumOfPixels = 0;
        for (int i = 0; i < horizontalHistogram.length; i++) {
            weight += i * horizontalHistogram[i];
            sumOfPixels += horizontalHistogram[i];
        }

        return sumOfPixels == 0 ? 0 : Math.round(weight/sumOfPixels);
    }

    public static int getVerticalCenter(Mat input){

        int[] verticalHistogram = getVerticalHistogram(input);
        int weight = 0;
        int sumOfPixels = 0;
        for (int i = 0; i < verticalHistogram.length; i++) {
            weight += i * verticalHistogram[i];
            sumOfPixels += verticalHistogram[i];
        }

        return sumOfPixels == 0 ? 0 : Math.round(weight/sumOfPixels);
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

}
