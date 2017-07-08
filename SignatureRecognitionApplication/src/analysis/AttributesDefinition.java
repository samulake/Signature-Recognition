package analysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import weka.core.Attribute;

public class AttributesDefinition {
	private HashSet<Attribute> attributeSet;

	public static final String ATTRIBUTES_FOLDER_PATH = "./data/attributes/";

	public AttributesDefinition() {
		this.attributeSet = new HashSet<>();
		try {
			initializeAttributeSet();
		} catch (FileNotFoundException e) {
		}
	}

	public Set<Attribute> getAttributeSet() {
		return attributeSet;
	}

	public void setAttributeSet(HashSet<Attribute> testAttributeSet) {
		this.attributeSet = testAttributeSet;
	}

	private void initializeAttributeSet() throws FileNotFoundException {
		final File attributesFolder = new File(ATTRIBUTES_FOLDER_PATH);
		if (attributesFolder.exists()) {
			for (File attributeFile : attributesFolder.listFiles()) {
				String attributeName = attributeFile.getName();
				List<String> attributeValueList = readAttributeValues(attributeFile);
				putAttributeIntoSet(attributeName, attributeValueList);
			}
		} else
			throw new FileNotFoundException("Attributes folder not found!");
	}

	private void putAttributeIntoSet(String attributeName, List<String> attributeValueList) {
		if (attributeValueList.size() == 0) {
			this.attributeSet.add(new Attribute(attributeName));
		} else {
			this.attributeSet.add(new Attribute(attributeName, attributeValueList));
		}
	}

	private List<String> readAttributeValues(File attributeFile) throws FileNotFoundException {
		List<String> attributeValueList = new ArrayList<>();
		Scanner fileReader = new Scanner(attributeFile);
		while (fileReader.hasNextLine()) {
			attributeValueList.add(fileReader.nextLine());
		}
		fileReader.close();
		return attributeValueList;
	}
}
