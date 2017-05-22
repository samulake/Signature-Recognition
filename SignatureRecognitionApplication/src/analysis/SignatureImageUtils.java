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

    private static boolean isBlack(double[] pixel) {
        return pixel[0] == 255;
    }

    public static int[] getNormalizedHistogram(int[] histogram, int expectedWidth) {
        int normalizedHistogram[] = new int[expectedWidth];

        int firstNonZeroIndex = findFirstNotZeroElement(histogram);
        int lastNonZeroIndec = findLastNotZeroElement(histogram);

        for (int i = 0; i < expectedWidth; i++) {
            float division = (Float.valueOf(lastNonZeroIndec - firstNonZeroIndex) / expectedWidth) * i;
            int histogramIndex = firstNonZeroIndex + Math.round(division);
            normalizedHistogram[i] = histogram[histogramIndex < histogram.length ? histogramIndex : histogram.length - 1];
        }
        return normalizedHistogram;
    }

    private static int findFirstNotZeroElement(int[] histogram) {
        for (int i = 0; i < histogram.length; i++) {
            if (histogram[i] > 0) {
                return i;
            }
        }
        return 0;
    }

    private static int findLastNotZeroElement(int[] histogram) {
        for (int i = histogram.length - 1; i >= 0; i--) {
            if (histogram[i] > 0) {
                return i;
            }
        }
        return histogram.length;
    }

}
