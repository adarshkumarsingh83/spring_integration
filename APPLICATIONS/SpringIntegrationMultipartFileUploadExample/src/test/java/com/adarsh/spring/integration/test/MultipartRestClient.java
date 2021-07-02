package com.adarsh.spring.integration.test;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class MultipartRestClient {

	private static Logger logger = Logger.getLogger(MultipartRestClient.class);
	private static String uri = "http://localhost:8080/SpringIntegrationMultipartFileUploadExample/inboundAdapter.do";
	private static String resourcePath = "/adarsh.jpg";

	public static void main(String[] args) throws Exception{
		RestTemplate template = new RestTemplate();
		Resource s2logo = new ClassPathResource(resourcePath);
		MultiValueMap<String, Object> multipartMap = new LinkedMultiValueMap<String, Object>();
		multipartMap.add("name", "Adarsh kumar");
		multipartMap.add("image", s2logo);
		logger.info("Created multipart request: " + multipartMap);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("multipart", "form-data"));
		HttpEntity<Object> request = new HttpEntity<Object>(multipartMap, headers);
		logger.info("Posting request to: " + uri);
		ResponseEntity<?> httpResponse = template.exchange(uri, HttpMethod.POST, request, Object.class);
		if (!httpResponse.getStatusCode().equals(HttpStatus.OK)){
			logger.error("Problems with the request. Http status: " + httpResponse.getStatusCode());
		}
	}
}
