package test;

import preprocessing.SignatureImageProcessor;
import preprocessing.SignatureImageProcessorDecorator;

public class LoggerDecorator extends SignatureImageProcessorDecorator {
	private long durationTime;
	
	public LoggerDecorator(SignatureImageProcessor component) {
		super(component);
	}

	@Override
	public Object getImage() {
		durationTime = System.currentTimeMillis();
		System.out.println("getImage() from class " + super.getComponent().getClass() + " is called.");
		System.out.println("getImage() from class " + super.getComponent().getClass() + " has been completed in " 
				+ (System.currentTimeMillis()-durationTime)/1000 + "seconds.");
		return super.getImage();
	}

	@Override
	public void processImage(String sourcePath) {
		durationTime = System.currentTimeMillis();
		System.out.println("processImage() from class " + super.getComponent().getClass() + " is called.");
		super.processImage(sourcePath);
		System.out.println("processImage() from class " + super.getComponent().getClass() + " has been completed in " + (System.currentTimeMillis()-durationTime)/1000 + "seconds. ");
	}

	@Override
	public void readImage(String sourcePath) {
		durationTime = System.currentTimeMillis();
		System.out.println("readImage() from class " + super.getComponent().getClass() + " is called.");
		super.readImage(sourcePath);
		System.out.println("processImage() from class " + super.getComponent().getClass() + " has been completed in " + (System.currentTimeMillis()-durationTime)/1000 + "seconds. ");
	}

	@Override
	public void eliminateBackground() {
		durationTime = System.currentTimeMillis();
		System.out.println("eliminateBackground() from class " + super.getComponent().getClass() + " is called.");
		super.eliminateBackground();
		System.out.println("eliminateBackground() from class " + super.getComponent().getClass() + " has been completed in " + (System.currentTimeMillis()-durationTime)/1000 + "seconds. ");
	}

	@Override
	public void reduceNoise() {
		durationTime = System.currentTimeMillis();
		System.out.println("reduceNoise() from class " + super.getComponent().getClass() + " is called.");
		super.reduceNoise();
		System.out.println("reduceNoise() from class " + super.getComponent().getClass() + " has been completed in " + (System.currentTimeMillis()-durationTime)/1000 + "seconds. ");
	}

	@Override
	public void normalizeWidth(int width) {
		durationTime = System.currentTimeMillis();
		System.out.println("normalizeWidth() from class " + super.getComponent().getClass() + " is called.");
		super.normalizeWidth(width);
		System.out.println("normalizeWidth() from class " + super.getComponent().getClass() + " has been completed in " + (System.currentTimeMillis()-durationTime)/1000 + "seconds. ");
		
	}

	@Override
	public void thin() {
		durationTime = System.currentTimeMillis();
		System.out.println("thin() from class " + super.getComponent().getClass() + " is called.");
		super.thin();
		System.out.println("thin() from class " + super.getComponent().getClass() + " has been completed in " + (System.currentTimeMillis()-durationTime)/1000 + "seconds. ");
	}
}
