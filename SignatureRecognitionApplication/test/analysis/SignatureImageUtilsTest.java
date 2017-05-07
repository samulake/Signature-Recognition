package analysis;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import java.util.Arrays;
import static org.junit.Assert.*;
import static org.opencv.core.CvType.CV_32S;

public class SignatureImageUtilsTest {

    final int SIZE = 10;
    final int WHITE_VALUE = 0;
    final int BLACK_VALUE = 255;

    @BeforeClass
    public static void init() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    @Test
    public void checkIfReturnsZerosWhenImageIsWhite() {

        int counter = 0;

        Mat image = new Mat(SIZE, SIZE, CV_32S);
        fillMatAsSolid(image, WHITE_VALUE);
        int[] histogram = SignatureImageUtils.getVerticalHistogram(image);
        for (int column = 0; column < histogram.length; column++) {
            counter += histogram[column];
        }

        assertEquals("Should return Mat of zeros", 0, counter);
    }

    @Test
    public void checkIfResurnsOnesWhenImageIsBlack() {

        int counter = 0;

        Mat image = new Mat(SIZE, SIZE, CV_32S);
        fillMatAsSolid(image, BLACK_VALUE);
        int[] histogram = SignatureImageUtils.getVerticalHistogram(image);
        for (int column = 0; column < histogram.length; column++) {
            counter += histogram[column];
        }

        assertEquals("Should return Mat of ones", SIZE*SIZE, counter);
    }

    @Test
    public void checkIfCountsCorrectInEye() {

        int counter = 0;

        Mat image = new Mat(SIZE, SIZE, CV_32S);
        for (int i = 0; i < image.height(); i++){
                image.put(i, i, BLACK_VALUE);
        }
        int[] histogram = SignatureImageUtils.getVerticalHistogram(image);
        for (int column = 0; column < histogram.length; column++) {
            counter += histogram[column];
        }

        int[] expectation = new int[SIZE];
        Arrays.fill(expectation, 1);

        assertEquals("Should return Mat of ones", SIZE, counter);
        assertArrayEquals("Does not count eye properly", expectation, histogram);
    }

    @Test
    public void checkIfReturnedMatFileHasCorrectWidth() {
        Mat image = new Mat(SIZE, SIZE, CV_32S);
        int[] histogram = SignatureImageUtils.getVerticalHistogram(image);
        assertEquals("Returned Mat shoud have the same width as original", SIZE, histogram.length);
    }

    private void fillMatAsSolid(Mat mat, int value){
        for (int i = 0; i < mat.height(); i++){
            for(int j = 0; j < mat.width(); j++){
                mat.put(i, j, value);
            }
        }
    }


}