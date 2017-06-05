package analysis;

import java.awt.List;
import java.util.ArrayList;

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
		
		ArrayList<Mat> regionList = splitMatrix(image, numberOfColumns);
		int [] n = new int [regionList.size()];
		
		for ( int i = 0 ; i < regionList.size() ; i++) {
			n[i] = countBlackPixels(regionList.get(i));
			//System.out.printf("%d - %d\n", i, n[i]);
		}
		return n;		
	}
	
	private ArrayList<Mat> splitMatrix(Mat image, int numberOfColumns) {		
		int startColumn, endColumn, startRow, endRow;		
		int numberOfRows = 2;
		int columnWidth = image.cols() / numberOfColumns;
		int rowHeight = image.rows() / 2;				
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
	
	private int countBlackPixels(Mat matrix) {
		double [] pixel;
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

}