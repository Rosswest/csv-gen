package com.rosswest.csv_gen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CsvBuilder {

	private static final char[] alphabet =  {'a','b','c','d','e','f','g','h','i','j','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	
	public File createAndWriteNLineFile(CsvBuilderConfiguration config) throws IOException {
		File outputDirectory = config.getOutputDirectory();
		int linesToGenerate = config.getLinesToGenerate();
		int fieldCount = config.getFieldCount();
		return createAndWriteNLineFile(outputDirectory, linesToGenerate, fieldCount);
	}
	
	public File createAndWriteNLineFile(File outputDirectory, int linesToGenerate, int fieldCount) throws IOException {
		// create file
		String outputFilePath = outputDirectory.getAbsolutePath() + "/csv-gen-output-" + UUID.randomUUID() + ".csv";
		File outputFile = new File(outputFilePath);
		
		// set up file writers
		FileWriter writer = new FileWriter(outputFile);
		BufferedWriter buffer = new BufferedWriter(writer);

		// generate and write lines
		for (int i = 0; i < linesToGenerate; i++) {
			String line = buildLineToGenerate(fieldCount, i);
			buffer.write(line);
		}
		
		// close file readers
		buffer.close();
		writer.close();
		
		return outputFile;
	}

	private String buildLineToGenerate(int fieldCount, int lineIndex) {
		List<String> tokens = new ArrayList<String>();
		for (int i = 0; i < fieldCount; i++) {
			char alphabetChar = getCycledAlphabetChar(i);
			String token = alphabetChar + Integer.toString(lineIndex);
			tokens.add(token);
		}

		String line = String.join(",", tokens) + "\n";
		return line;
	}
	
	private char getCycledAlphabetChar(int index) {
		int modulo = alphabet.length;
		int cycledIndex= index % modulo;
		return alphabet[cycledIndex];
	}

}
