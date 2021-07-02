package com.adarsh.spring.integration.test;

import com.adarsh.spring.integration.entities.EmployeeList;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * RestHttpClientTest.java: Functional Test to test the REST HTTP Path usage. This test requires
 * rest-http application running in HTTP environment.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/configuration/applicationContext-integrationOutbound.xml"})
public class RestHttpClientTest {

	@Autowired
	private RestTemplate restTemplate;
	private HttpMessageConverterExtractor<EmployeeList> responseExtractor;
	private static Logger logger = Logger.getLogger(RestHttpClientTest.class);
	@Autowired
	private Jaxb2Marshaller marshaller;
	@Autowired
	private ObjectMapper jaxbJacksonObjectMapper;

	@Before
	public void setUp() {
		responseExtractor = new HttpMessageConverterExtractor<EmployeeList>(EmployeeList.class, restTemplate.getMessageConverters());

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8");
		properties.put(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.setMarshallerProperties(properties);
	}
	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetEmployeeAsXml() throws Exception{

		Map<String, Object> employeeSearchMap = getEmployeeSearchMap("0");

		final String fullUrl = "http://localhost:8080/SpringIntegrationWebServiceExample/services/employee/{id}/search";

		EmployeeList employeeList = restTemplate.execute(fullUrl, HttpMethod.GET,
				new RequestCallback() {
					@Override
					public void doWithRequest(ClientHttpRequest request) throws IOException {
						HttpHeaders headers = getHttpHeadersWithUserCredentials(request);
						headers.add("Accept", "application/xml");
					}
		}, responseExtractor, employeeSearchMap);

		logger.info("The employee list size :"+employeeList.getEmployee().size());

		StringWriter sw = new StringWriter();
		StreamResult sr = new StreamResult(sw);

		marshaller.marshal(employeeList, sr);
		logger.info(sr.getWriter().toString());
		assertTrue(employeeList.getEmployee().size() > 0);
	}

	private Map<String, Object> getEmployeeSearchMap(String id) {
		Map<String, Object> employeeSearchMap = new HashMap<String, Object>();
		employeeSearchMap.put("id", id);
		return employeeSearchMap;
	}

	@Test
	public void testGetEmployeeAsJson() throws Exception{
		Map<String, Object> employeeSearchMap = getEmployeeSearchMap("0");

		final String fullUrl = "http://localhost:8080/SpringIntegrationWebServiceExample/services/employee/{id}/search?format=json";
		HttpHeaders headers = getHttpHeadersWithUserCredentials(new HttpHeaders());
		headers.add("Accept", "application/json");
		HttpEntity<Object> request = new HttpEntity<Object>(headers);

		ResponseEntity<?> httpResponse = restTemplate.exchange(fullUrl, HttpMethod.GET, request, EmployeeList.class, employeeSearchMap);
		logger.info("Return Status :"+httpResponse.getHeaders().get("X-Return-Status"));
		logger.info("Return Status Message :"+httpResponse.getHeaders().get("X-Return-Status-Msg"));
		assertTrue(httpResponse.getStatusCode().equals(HttpStatus.OK));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		jaxbJacksonObjectMapper.writeValue(out, httpResponse.getBody());
		logger.info(new String(out.toByteArray()));
	}

	private HttpHeaders getHttpHeadersWithUserCredentials(ClientHttpRequest request){
		return (getHttpHeadersWithUserCredentials(request.getHeaders()));
	}

	private HttpHeaders getHttpHeadersWithUserCredentials(HttpHeaders headers){

		final String username = "SPRING";
        final String password = "spring";

		final String combinedUsernamePassword = username+":"+password;
		byte[] base64Token = Base64.encode(combinedUsernamePassword.getBytes());
		String base64EncodedToken = new String (base64Token);
		//adding Authorization header for HTTP Basic authentication
		headers.add("Authorization","Basic  "+base64EncodedToken);
		return headers;
	}
}


