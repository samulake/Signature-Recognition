package analysis;

import org.opencv.core.Mat;

import java.util.Arrays;

public class SignatureImageUtils {

    private static int blackValue = 0;

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

    private static boolean isBlack(double[] pixel) {
        return pixel[0] == blackValue;
    }

}
