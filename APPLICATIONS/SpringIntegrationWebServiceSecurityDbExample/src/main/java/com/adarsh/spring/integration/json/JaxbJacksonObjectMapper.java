package com.adarsh.spring.integration.json;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;


/**
 * JaxbJacksonObjectMapper.java: This is the custom JAXB JSON ObjectMapper
 */
public class JaxbJacksonObjectMapper extends ObjectMapper {

	/**
	 * Annotation introspector to use for serialization process
	 * is configured separately for serialization and deserialization purposes
	 */
	public JaxbJacksonObjectMapper() {
		  final AnnotationIntrospector jacksonAnnotationInspector
		      = new JacksonAnnotationIntrospector();
		  super.getDeserializationConfig()
		       .with(jacksonAnnotationInspector);
		  super.getSerializationConfig()
		       .with(jacksonAnnotationInspector);

	}
}
