/*
 * Copyright 2002-2010 the original author or authors.
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

package org.springframework.integration.samples.filecopy;

import com.adarsh.spring.FileCopierService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Demonstrating the file copy scenario using text-based source and target.
 * See the 'fileCopyDemo-text.xml' configuration file for details.
 *

 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/configuration/fileCopyDemo-text.xml"})
public class TextFileCopyTest {

    @Autowired
    private FileCopierService fileCopierService;

	@Test
	public void testTextBasedCopy() throws Exception{
        fileCopierService.displayDirectories();
		Thread.sleep(50000);
	}
	
}
