package analysis;

import org.opencv.core.Mat;

public class DefaultClassSupplier implements AttributeSupplier {

	@Override
	public Double apply(Mat image) {
		return 0.0;
	}

}
