package test;

import preprocessing.SignatureImageProcessor;
import preprocessing.SignatureImageProcessorDecorator;

public class LoggerDecorator extends SignatureImageProcessorDecorator {
	
	public LoggerDecorator(SignatureImageProcessor component) {
		super(component);
	}

	@Override
	public Object getImage() {
		System.out.println("getImage() from class " + super.getComponent().getClass() + " is called.");
		System.out.println("getImage() from class " + super.getComponent().getClass() + " is completed.");
		return super.getImage();
	}

	@Override
	public void processImage(String sourcePath) {
		System.out.println("processImage() from class " + super.getComponent().getClass() + " is called.");
		super.processImage(sourcePath);
		System.out.println("processImage() from class " + super.getComponent().getClass() + " is completed.");
	}

	@Override
	public void readImage(String sourcePath) {
		System.out.println("readImage() from class " + super.getComponent().getClass() + " is called.");
		super.readImage(sourcePath);
		System.out.println("processImage() from class " + super.getComponent().getClass() + " is completed.");
	}

	@Override
	public void eliminateBackground() {
		System.out.println("eliminateBackground() from class " + super.getComponent().getClass() + " is called.");
		super.eliminateBackground();
		System.out.println("eliminateBackground() from class " + super.getComponent().getClass() + " is completed.");
	}

	@Override
	public void reduceNoise() {
		System.out.println("reduceNoise() from class " + super.getComponent().getClass() + " is called.");
		super.reduceNoise();
		System.out.println("reduceNoise() from class " + super.getComponent().getClass() + " is completed.");
	}

	@Override
	public void normalizeWidth(int width) {
		System.out.println("normalizeWidth() from class " + super.getComponent().getClass() + " is called.");
		super.normalizeWidth(width);
		System.out.println("normalizeWidth() from class " + super.getComponent().getClass() + " is completed.");
		
	}

	@Override
	public void thin() {
		System.out.println("thin() from class " + super.getComponent().getClass() + " is called.");
		super.thin();
		System.out.println("thin() from class " + super.getComponent().getClass() + " is completed.");
	}
}
