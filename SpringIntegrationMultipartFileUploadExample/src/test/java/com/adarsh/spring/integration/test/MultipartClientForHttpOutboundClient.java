package com.adarsh.spring.integration.test;

import com.adarsh.spring.integration.MultipartRequestGateway;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class MultipartClientForHttpOutboundClient {

	private static Logger logger = Logger.getLogger(MultipartClientForHttpOutboundClient.class);
	private static String resourcePath = "/adarsh.jpg";

	public static void main(String[] args) throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/configuration/applicationContext-outbound.xml");
		Resource s2logo = new ClassPathResource(resourcePath);
		Map<String, Object> multipartMap = new HashMap<String, Object>();
		multipartMap.put("name", new String[]{"Adarsh kumar", "ESpark"});
		multipartMap.put("image", s2logo);
		logger.info("Created multipart request: " + multipartMap);
		MultipartRequestGateway requestGateway = context.getBean("requestGateway", MultipartRequestGateway.class);
        ResponseEntity responseEntity = requestGateway.postMultipartRequest(multipartMap);
		System.out.println("Replied with HttpStatus code: " + responseEntity.getStatusCode());
	}
}
