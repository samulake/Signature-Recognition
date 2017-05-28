package analysis;

import org.opencv.core.Mat;

import java.util.Arrays;

public class SignatureImageUtils {

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

    //TODO po megre powinny byc ustawione popawne wartości na 0
    public static boolean isBlack(double pixelValue) {
        return pixelValue == 0;
    }

    private static boolean isBlack(double[] pixel) {
        return pixel[0] == 255;
    }

}
