package com.adarsh.spring.file.test;

import com.adarsh.spring.FileCopierService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Demonstrating the file copy scenario using file-based source and target.
 * See the 'fileCopyDemo-file.xml' configuration file for details.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/configuration/fileCopyDemo-file.xml"})
public class FileBasedFileCopyTest {

    @Autowired
    private FileCopierService fileCopierService;

	@Test
	public void testFileBasedCopy() throws Exception {
        fileCopierService.displayDirectories();
        Thread.sleep(500000000);
	}

}
