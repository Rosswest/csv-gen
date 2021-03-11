package com.rosswest.csv_gen;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import jdk.internal.org.jline.utils.Log;

public class Main {

	private static final int DEFAULT_FIELD_COUNT = 20;
	private static final int DEFAULT_NUMBER_OF_LINES_TO_GENERATE = 100000;
	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String... args) {
    
        // get the config for the csv builder
        CsvBuilderConfiguration config = generateCsvBuilderConfiguration(args);
        LOGGER.info("Proceeding with configuration: " + config.toString());

        // create the csv file
        try {
        	long writeStart = System.currentTimeMillis();
            CsvBuilder builder = new CsvBuilder();
			File outputFile = builder.createAndWriteNLineFile(config);
			long writeEnd = System.currentTimeMillis();
			long duration = writeEnd - writeStart;
			String completionMessage = "File written to " + outputFile.getAbsolutePath() + " after " + duration + "ms";
	        LOGGER.info(completionMessage);
			JOptionPane.showMessageDialog(null, completionMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}

        
    }

	private static CsvBuilderConfiguration generateCsvBuilderConfiguration(String[] args) {
		CsvBuilderConfiguration config = null;
        try {
            config = parseCsvBuilderConfigFromCmdArgs(args);
        } catch (InvalidParameterException e) {
        	LOGGER.info("Invalid Command Line Parameters. Falling back to user prompt.");
            config = getBuilderConfigFromUserInputPrompts();
        }
        
        return config;
	}

	private static CsvBuilderConfiguration parseCsvBuilderConfigFromCmdArgs(String[] args) throws InvalidParameterException {
		if (args.length < 3) {
			throw new InvalidParameterException();
		} else {
			
			//parse values from command-line arguments
			String lineCountArg = args[0];
			String fieldCountArg = args[1];
			String outputDirectoryArg = args[2];
			int linesToGenerate = Integer.parseInt(lineCountArg);
			int fieldCount = Integer.parseInt(fieldCountArg);
			File outputDirectory = new File(outputDirectoryArg);

			// check if they are valid
			boolean linesToGenerateValid = (linesToGenerate >= 1);
			boolean fieldCountValid = (fieldCount >= 1);
			boolean outputDirectoryValid = outputDirectory.isDirectory();
			boolean allValid = (linesToGenerateValid && fieldCountValid && outputDirectoryValid);

			if (allValid) {
		        CsvBuilderConfiguration config = new CsvBuilderConfiguration(outputDirectory, linesToGenerate, fieldCount);
		        return config;
			} else {
				//report on invalid parameters
				List<String> invalidFields = new ArrayList<String>();
				if (!linesToGenerateValid) {
					invalidFields.add("linesToGenerate (" + linesToGenerate + ")");
				}
				if (!fieldCountValid) {
					invalidFields.add("fieldCount (" + fieldCount + ")");
				}
				if (!outputDirectoryValid) {
					invalidFields.add("outputDirectory (" + outputDirectory + ")");
				}
				String invalidFieldsMessage = String.join(", ", invalidFields);
				JOptionPane.showMessageDialog(null, "Input parameters invalid: " + invalidFieldsMessage);
				throw new InvalidParameterException();
			}
		}
	}
	
	private static CsvBuilderConfiguration getBuilderConfigFromUserInputPrompts()  {
       
    	LOGGER.info("Prompting user for input parameters");

		// get number of lines to generate
        String linesToGenerateString = JOptionPane.showInputDialog(null, "How many lines to generate?", DEFAULT_NUMBER_OF_LINES_TO_GENERATE);
        if (linesToGenerateString == null) {
        	LOGGER.info("Invalid number: " + linesToGenerateString);
        	System.exit(0);
        }
        int linesToGenerate = Integer.parseInt(linesToGenerateString);

        // get number of fields to generate
        String fieldCountString = JOptionPane.showInputDialog(null, "How many fields to generate?", DEFAULT_FIELD_COUNT);
        if (fieldCountString == null) {
        	LOGGER.info("Invalid number: " + fieldCountString);
        	System.exit(0);
        }
        int fieldCount = Integer.parseInt(fieldCountString);
        
        // get the directory to create the file in
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.showOpenDialog(null);
        File outputDirectory = fc.getSelectedFile();
        if (outputDirectory == null) {
        	LOGGER.info("No directory selected: " + outputDirectory);
        	System.exit(0);
        }
        
        CsvBuilderConfiguration config = new CsvBuilderConfiguration(outputDirectory, linesToGenerate, fieldCount);
        return config;
	}
    
    
    
    
    
    
}
