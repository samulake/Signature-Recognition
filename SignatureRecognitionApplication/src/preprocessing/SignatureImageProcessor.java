package preprocessing;

public class SignatureImageProcessor implements ImageProcessor {

	public SignatureImageProcessorImplementor implementor;

	public SignatureImageProcessor(SignatureImageProcessorImplementor implementor){
		this.implementor = implementor;
	}

	public void finalize() throws Throwable {

	}

	public Object getImage(){
		return this.implementor.getImage();
	}

	public final void processImage(String sourcePath){
		implementor.processImage(sourcePath);
	}
}