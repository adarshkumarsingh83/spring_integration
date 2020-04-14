/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package spring;

import java.io.File;

import org.slf4j.Logger;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.stereotype.Service;

/**
 * Displays the names of the input and output directories.
 *
 */
@Service(value = "fileCopierService")
public class FileCopierService {

    private static final Logger LOGGER= org.slf4j.LoggerFactory.getLogger(FileCopierService.class);

    @Autowired
    private ApplicationContext context;

	public void displayDirectories() {
		final File inputDirectory = (File) new DirectFieldAccessor(context.getBean(FileReadingMessageSource.class)).getPropertyValue("directory");
        final LiteralExpression expression = (LiteralExpression) new DirectFieldAccessor(context.getBean(FileWritingMessageHandler.class)).getPropertyValue("destinationDirectoryExpression");
        final File outputDirectory = new File(expression.getValue());
		LOGGER.info("Input directory is: " + inputDirectory.getAbsolutePath());
		LOGGER.info("Output directory is: " + outputDirectory.getAbsolutePath());
		LOGGER.info("===================================================");
	}

}
