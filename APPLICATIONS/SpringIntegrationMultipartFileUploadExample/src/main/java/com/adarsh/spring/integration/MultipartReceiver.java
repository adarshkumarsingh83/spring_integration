package com.adarsh.spring.integration;

import org.apache.log4j.Logger;
import org.springframework.integration.http.multipart.UploadedMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import java.io.File;
import java.util.LinkedList;


public class MultipartReceiver {

    private static Logger logger = Logger.getLogger(MultipartReceiver.class);

    public void receive(LinkedMultiValueMap<String, Object> multipartRequest) throws Exception {
        logger.info("Successfully received multipart request: " + multipartRequest);
        for (String elementName : multipartRequest.keySet()) {
            if (elementName.equals("name")) {
                LinkedList value = (LinkedList) multipartRequest.get("name");
                String[] multiValues = (String[]) value.get(0);
                for (String companyName : multiValues) {
                    logger.info(elementName + " - " + companyName);
                }
            } else if (elementName.equals("image")) {
                final UploadedMultipartFile uploadedMultipartFile = ((UploadedMultipartFile) multipartRequest.getFirst("image"));
                logger.info(elementName + " - as UploadedMultipartFile: " + uploadedMultipartFile.getOriginalFilename());
                final byte[] fileBytes = uploadedMultipartFile.getBytes();
                final String fileName = "d:"+File.separator +uploadedMultipartFile.getOriginalFilename();
                final File file = new File(fileName);
                logger.info("Coping File to Location "+file.getAbsoluteFile());
                uploadedMultipartFile.transferTo(file);
            }
        }
    }
}
