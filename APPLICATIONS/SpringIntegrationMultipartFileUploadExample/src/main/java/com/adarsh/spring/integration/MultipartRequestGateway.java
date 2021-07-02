package com.adarsh.spring.integration;

import org.springframework.http.ResponseEntity;
import java.util.Map;

public interface MultipartRequestGateway {

	public ResponseEntity postMultipartRequest(Map<String, Object> multipartRequest);
}
