
package com.adarsh.spring;

import org.slf4j.Logger;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Displays the names of the input and output directories.
 *
 */
@Service(value = "fileCopierService")
public class FileCopierService {

    private static final Logger LOGGER= org.slf4j.LoggerFactory.getLogger(FileCopierService.class);

    @Autowired
    private ApplicationContext context;

    @Value("${doc.output.dir.path}")
    private String docOutputDirPath;

    @Value("${txt.output.dir.path}")
    private String txtOutputDirPath;

	public void displayDirectories() {
		final File inputDirectory = (File) new DirectFieldAccessor(context.getBean(FileReadingMessageSource.class)).getPropertyValue("directory");
       // final LiteralExpression expression = (LiteralExpression) new DirectFieldAccessor(context.getBean(FileWritingMessageHandler.class)).getPropertyValue("destinationDirectoryExpression");
        final File outputDirectory1 = new File(txtOutputDirPath);
        final File outputDirectory2 = new File(docOutputDirPath);
		LOGGER.info("Input directory is: " + inputDirectory.getAbsolutePath());
		LOGGER.info("Output directory is: " + outputDirectory1.getAbsolutePath());
		LOGGER.info("Output directory is: " + outputDirectory2.getAbsolutePath());
		LOGGER.info("===================================================");
	}

}
