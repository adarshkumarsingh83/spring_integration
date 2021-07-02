package com.adarsh.spring;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * A class providing several handling methods for different types of payloads.
 *
 */
@Component(value = "fileHandler")
public class FileHandler {

    private static final Logger LOGGER= org.slf4j.LoggerFactory.getLogger(FileHandler.class);

	public String handleString(String input) {
		LOGGER.info("Copying text: " + input);
		return input.toUpperCase();
	}
	
	public File handleFile(File input) {
		LOGGER.info("Copying file: " + input.getAbsolutePath());
		return input;
	}
	
	public byte[] handleBytes(byte[] input) {
		LOGGER.info("Copying " + input.length + " bytes ...");
		return new String(input).toUpperCase().getBytes();
	}

}
