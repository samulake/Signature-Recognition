package analysis;

import org.opencv.core.Mat;
import org.opencv.core.Point;

public class SignatureImageFeatureExtractorImplementor implements SignatureImageFeatureExtractor {

	public SignatureImageFeatureExtractorImplementor(){

	}

	public void finalize() throws Throwable {

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

	
	public Point getHighestBlackPixel(Mat image) {
		double [] pixel = null; 
		int i, j = 0;
		Point p = null;
		
		for (i = 0; i < image.height(); i++) {
			for (j = 0; j < image.width(); j++){
				pixel = image.get(i, j);
				if (pixel[0] == 0.0f) {
					p = new Point(j, i);
					return p;
				}
			}
		}
		
		return p;
	}
	
	
	public Point getLowestBlackPixel(Mat image) {
		double [] pixel = null; 
		int i, j = 0;
		Point p = null;
		
		for (i = image.height() - 1; i >= 0 ; i--) {
			for (j = 0; j < image.width(); j++){
				pixel = image.get(i, j);
				if (pixel[0] == 0.0f) {
					p = new Point(j, i);
					return p;
				}
			}
		}
		
		return p;
	}
	
	public Point getLeftestBlackPixel(Mat image) {
		double [] pixel = null; 
		int i, j = 0;
		Point p = null;
		
		for (i = 0; i < image.width(); i++) {
			for (j = 0; j < image.height(); j++){
				pixel = image.get(j, i);
				if (pixel[0] == 0.0f) {
					p = new Point(i, j);
					return p;
				}
			}
		}
		
		return p;
	}
	
	public Point getRightestBlackPixel(Mat image) {
		double [] pixel = null; 
		int i, j = 0;
		Point p = null;
		
		for (i = image.width() - 1; i >= 0 ; i--) {
			for (j = 0; j < image.height(); j++){
				pixel = image.get(j, i);
				if (pixel[0] == 0.0f) {
					p = new Point(i, j);
					return p;
				}
			}
		}
		
		return p;
	}
	
	
	public float getHeightWidthRatio(Mat image) {
		return (float) (getLowestBlackPixel(image).y - getHighestBlackPixel(image).y) / (float) (getRightestBlackPixel(image).x - getLeftestBlackPixel(image).x);
	}
	
	
	
	
	
}