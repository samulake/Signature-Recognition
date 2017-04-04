package analysis;

import org.opencv.core.Mat;


/**
 * @author Eryk
 * @version 1.0
 * @created 06-mar-2017 18:20:28
 */
public class OpenCVSignatureImageFeatureExtractorImplementor extends SignatureImageFeatureExtractorImplementor {

	public OpenCVSignatureImageFeatureExtractorImplementor(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	public Object extractFeatures(){
		return null;
	}

	public Object extractGlobalFeatures(){
		return null;
	}

	public Object extractGridFeatures(){
		return null;
	}

	public Object extractMaskFeatures(){
		return null;
	}
	
	public int [] numberOfBlackPixelsPerRegion (Mat image, int numberOfColumns) {
		Mat [] matrices = splitMatrix(image, numberOfColumns);
		int [] n = new int [matrices.length];
		
		for ( int i = 0 ; i < matrices.length ; i++) {
			n[i] = countBlackPixels(matrices[i]);
			//System.out.printf("%d - %d\n", i, countBlackPixels(matrices[i]));
		}
		return n;		
	}
	
	private Mat [] splitMatrix(Mat image, int numberOfColumns) {		
		int startColumn, endColumn, startRow, endRow;		
		int numberOfRows = 2;
		int numberOfRegions = numberOfRows * numberOfColumns;
		int imageColumns = image.cols();
		int imageRows = image.rows();		
		int columnWidth = imageColumns / numberOfColumns;
		int rowHeight = imageRows / 2;				
		Mat [] matrices = new Mat [numberOfRegions];
				
		for (int rc = 0; rc < numberOfRows; rc++) {
			startRow = rc * rowHeight;		    
		    if ( rc == numberOfRows - 1)
				endRow = imageRows;
		    else
		    	endRow = (rc + 1) * rowHeight;			
			for (int cc = 0; cc < numberOfColumns; cc++) {				
				startColumn = cc * columnWidth;				
				if ( cc == numberOfColumns - 1)
					endColumn = imageColumns;
				else
					endColumn = (cc + 1) * columnWidth;				
				matrices[cc + rc * numberOfColumns] = image.submat(startRow, endRow, startColumn, endColumn);
			}
		}
		return matrices;		
	}
	
	private int countBlackPixels(Mat matrix) {
		double [] pixel;
		boolean b = false;
		boolean g = false;
		boolean r = false;
		int numberOfBlackPixels = 0;
						
		for (int j = 0; j < matrix.cols(); j++) {
			for (int k = 0; k < matrix.rows(); k++) {		
					pixel = matrix.get(k, j);
							
					b = pixel[0] == 0.0 ? true : false;
					g = pixel[1] == 0.0 ? true : false;
					r = pixel[2] == 0.0 ? true : false;
							
					if (b && g && r)
						numberOfBlackPixels++;		
			}
		}
		return numberOfBlackPixels;
	}

}