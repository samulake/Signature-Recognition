package analysis;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import preprocessing.ImageSaverDecorator;
import preprocessing.OpenCVSignatureImageProcessor;
import preprocessing.SignatureImageProcessor;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static org.opencv.core.CvType.CV_32S;

public class SignatureImageUtilsTest {

    final int SIZE = 10;
    final int WHITE_VALUE = 255;
    final int BLACK_VALUE = 0;

    private final int numberOfTests = 10;
    private SignatureImageProcessor testedClass;

    @BeforeClass
    public static void init() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    @Test(timeout = 1000 * numberOfTests)

    public void testProcessImage() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        final String testDataFolderPath = "./testData/";
        final String imageExtention = ".jpg";

        testedClass = new ImageSaverDecorator(new OpenCVSignatureImageProcessor());

        for (int testID = 0; testID < numberOfTests; testID++) {
            String inputFilePath = "./testData/processImage/histogram_test/testImage" + testID + "Result" + testID + imageExtention;
            testedClass.processImage(inputFilePath);
            Mat resultImage = (Mat) testedClass.getImage();
            SignatureImageUtils.getHorizontalHistogram(resultImage);
            SignatureImageUtils.getVerticalHistogram(resultImage);
        }
    }

    //Horizontal and Vertical center
    @Test
    public void whenImageIsWhiteCenterIsInZero(){
        Mat image = new Mat(SIZE, SIZE, CV_32S);
        fillMatAsSolid(image, WHITE_VALUE);
        assertEquals("Should return x = 0", 0, SignatureImageUtils.getHorizontalCenter(image));
        assertEquals("Should return y = 0", 0, SignatureImageUtils.getVerticalCenter(image));
    }

    @Test
    public void whenImageIsBlackCenterIsInTheMiddle(){
        Mat image = new Mat(SIZE, SIZE, CV_32S);
        fillMatAsSolid(image, BLACK_VALUE);
        assertEquals(SIZE/2, SignatureImageUtils.getHorizontalCenter(image), 1);
        assertEquals(SIZE/2, SignatureImageUtils.getVerticalCenter(image), 1);
    }

    @Test
    public void whenImageHasLineReturnItsMiddle(){
        Mat image = new Mat(SIZE, SIZE, CV_32S);
        fillMatAsSolid(image, WHITE_VALUE);
        image.put(3, 3, BLACK_VALUE);
        image.put(3, 4, BLACK_VALUE);
        image.put(3, 5, BLACK_VALUE);
        image.put(3, 6, BLACK_VALUE);
        image.put(3, 7, BLACK_VALUE);
        assertEquals(5, SignatureImageUtils.getHorizontalCenter(image));
        assertEquals(3, SignatureImageUtils.getVerticalCenter(image));
    }

    @Test
    public void whenImageHasMiterLineReturnItsMiddle(){
        Mat image = new Mat(SIZE, SIZE, CV_32S);
        fillMatAsSolid(image, WHITE_VALUE);
        image.put(2, 2, BLACK_VALUE);
        image.put(3, 3, BLACK_VALUE);
        image.put(4, 4, BLACK_VALUE);
        image.put(5, 5, BLACK_VALUE);
        image.put(6, 6, BLACK_VALUE);
        assertEquals(4, SignatureImageUtils.getHorizontalCenter(image));
        assertEquals(4, SignatureImageUtils.getVerticalCenter(image));
    }

    @Test
    public void whenImageHasTwoMiterLinesReturnItsMiddle(){
        Mat image = new Mat(SIZE, SIZE, CV_32S);
        fillMatAsSolid(image, WHITE_VALUE);
        image.put(2, 2, BLACK_VALUE);
        image.put(3, 3, BLACK_VALUE);
        image.put(4, 4, BLACK_VALUE);
        image.put(5, 5, BLACK_VALUE);
        image.put(6, 6, BLACK_VALUE);

        image.put(2, 4, BLACK_VALUE);
        image.put(3, 5, BLACK_VALUE);
        image.put(4, 6, BLACK_VALUE);
        image.put(5, 7, BLACK_VALUE);
        image.put(6, 8, BLACK_VALUE);

        assertEquals(5, SignatureImageUtils.getHorizontalCenter(image));
        assertEquals(4, SignatureImageUtils.getVerticalCenter(image));
    }

    //Edge point number test
    @Test
    public void whenImageIsBlackReturnsZeroEdgePoints(){
        Mat image = new Mat(SIZE, SIZE, CV_32S);
        fillMatAsSolid(image, WHITE_VALUE);
        int counter = SignatureImageUtils.getEdgePointNumber(image);
        assertEquals("Should return Mat of zeros", 0, counter);
    }

    @Test
    public void whenImageIsWhiteReturnsZeroEdgePoints(){
        Mat image = new Mat(SIZE, SIZE, CV_32S);
        fillMatAsSolid(image, BLACK_VALUE);
        int counter = SignatureImageUtils.getEdgePointNumber(image);
        assertEquals("Should return Mat of zeros", 0, counter);
    }

    @Test
    public void whenImageHasOneBlackPixelReturnsOneEdgePoint(){
        Mat image = new Mat(SIZE, SIZE, CV_32S);
        fillMatAsSolid(image, WHITE_VALUE);
        image.put(3, 3, BLACK_VALUE);
        int counter = SignatureImageUtils.getEdgePointNumber(image);
        assertEquals("Should be only one pixe which has no neighbours", 0, counter);
    }

    @Test
    public void whenImageHasLineOfPixelsReturnsTwoEdgePoints(){
        //TODO po merge beda ustawione dobre wartosci WHITE_VALUE i BLACK_VALUE
        Mat image = new Mat(SIZE, SIZE, CV_32S);
        fillMatAsSolid(image, WHITE_VALUE);
        image.put(3, 3, BLACK_VALUE);
        image.put(3, 4, BLACK_VALUE);
        image.put(3, 5, BLACK_VALUE);
        image.put(3, 6, BLACK_VALUE);
        image.put(3, 7, BLACK_VALUE);
        int counter = SignatureImageUtils.getEdgePointNumber(image);
        assertEquals("Should be only one pixe which has no neighbours", 2, counter);
    }

    @Test
    public void checkIfHorizontalReturnsZerosWhenImageIsWhite() {

        int counter = 0;

        Mat image = new Mat(SIZE, SIZE, CV_32S);
        fillMatAsSolid(image, WHITE_VALUE);
        int[] histogram = SignatureImageUtils.getHorizontalHistogram(image);
        for (int column = 0; column < histogram.length; column++) {
            counter += histogram[column];
        }

        assertEquals("Should return Mat of zeros", 0, counter);
    }

    @Test
    public void checkIfHorizontalResurnsOnesWhenImageIsBlack() {

        int counter = 0;

        Mat image = new Mat(SIZE, SIZE, CV_32S);
        fillMatAsSolid(image, BLACK_VALUE);
        int[] histogram = SignatureImageUtils.getHorizontalHistogram(image);
        for (int column = 0; column < histogram.length; column++) {
            counter += histogram[column];
        }

        assertEquals("Should return Mat of ones", SIZE * SIZE, counter);
    }

    @Test
    public void checkIfHorizontalCountsCorrectInEye() {

        int counter = 0;

        Mat image = new Mat(SIZE, SIZE, CV_32S);
        fillMatAsSolid(image, WHITE_VALUE);
        for (int i = 0; i < image.height(); i++) {
            image.put(i, i, BLACK_VALUE);
        }
        int[] histogram = SignatureImageUtils.getHorizontalHistogram(image);
        for (int column = 0; column < histogram.length; column++) {
            counter += histogram[column];
        }

        int[] expectation = new int[SIZE];
        Arrays.fill(expectation, 1);

        assertEquals("Should return Mat of ones", SIZE, counter);
        assertArrayEquals("Does not count eye properly", expectation, histogram);
    }

    @Test
    public void checkIfHorizontalReturnedMatFileHasCorrectWidth() {
        int WIDTH = 15;
        Mat image = new Mat(SIZE, WIDTH, CV_32S);
        int[] histogram = SignatureImageUtils.getHorizontalHistogram(image);
        assertEquals("Returned Mat shoud have the same width as original", WIDTH, histogram.length);
    }

    @Test
    public void checkIfVerticalReturnsZerosWhenImageIsWhite() {

        int counter = 0;

        Mat image = new Mat(SIZE, SIZE, CV_32S);
        fillMatAsSolid(image, WHITE_VALUE);
        int[] histogram = SignatureImageUtils.getVerticalHistogram(image);
        for (int row = 0; row < histogram.length; row++) {
            counter += histogram[row];
        }

        assertEquals("Should return Mat of zeros", 0, counter);
    }

    @Test
    public void checkIfVerticalReturnsOnesWhenImageIsBlack() {

        int counter = 0;

        Mat image = new Mat(SIZE, SIZE, CV_32S);
        fillMatAsSolid(image, BLACK_VALUE);
        int[] histogram = SignatureImageUtils.getVerticalHistogram(image);
        for (int row = 0; row < histogram.length; row++) {
            counter += histogram[row];
        }

        assertEquals("Should return Mat of ones", SIZE * SIZE, counter);
    }

    @Test
    public void checkIfVerticalCountsCorrectInEye() {

        int counter = 0;

        Mat image = new Mat(SIZE, SIZE, CV_32S);
        fillMatAsSolid(image, WHITE_VALUE);
        for (int i = 0; i < image.height(); i++) {
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
    public void checkIfVerticalReturnedMatFileHasCorrectWidth() {
        int HEIGHT = 15;
        Mat image = new Mat(HEIGHT, SIZE, CV_32S);
        int[] histogram = SignatureImageUtils.getVerticalHistogram(image);
        assertEquals("Returned Mat shoud have the same width as original", HEIGHT, histogram.length);
    }

    private void fillMatAsSolid(Mat mat, int value) {
        for (int i = 0; i < mat.height(); i++) {
            for (int j = 0; j < mat.width(); j++) {
                mat.put(i, j, value);
            }
        }
    }
}