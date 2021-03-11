package com.rosswest.csv_gen;

import java.io.File;

public class CsvBuilderConfiguration {
	private File outputDirectory;
	private int linesToGenerate;
	private int fieldCount;
	
	public CsvBuilderConfiguration(File outputDirectory, int linesToGenerate, int fieldCount) {
		super();
		this.outputDirectory = outputDirectory;
		this.linesToGenerate = linesToGenerate;
		this.fieldCount = fieldCount;
	}
	
	public File getOutputDirectory() {
		return outputDirectory;
	}
	public void setOutputDirectory(File outputDirectory) {
		this.outputDirectory = outputDirectory;
	}
	public int getLinesToGenerate() {
		return linesToGenerate;
	}
	public void setLinesToGenerate(int linesToGenerate) {
		this.linesToGenerate = linesToGenerate;
	}
	public int getFieldCount() {
		return fieldCount;
	}
	public void setFieldCount(int fieldCount) {
		this.fieldCount = fieldCount;
	}

	@Override
	public String toString() {
		return "CsvBuilderConfiguration [outputDirectory=" + outputDirectory + ", linesToGenerate=" + linesToGenerate
				+ ", fieldCount=" + fieldCount + "]";
	}
}
