package com.voyanta.test.util;

import java.io.IOException;
import java.nio.charset.Charset;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.http.MediaType;

import com.voyanta.test.entities.OperationRequest;
import com.voyanta.test.entities.OperationResult;
 
public class JsonUtil {
 
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), 
    		MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
 
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        
        return mapper.writeValueAsBytes(object);
    }
    
    public static Object convertJsonBytesToObject(byte [] jsonBytes, Class<?> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        
        Object object = mapper.readValue(jsonBytes, clazz);
        
        return object;
    }
    
	public static byte[] createOperationResultInJson(String id, double [] result) throws IOException {
		OperationResponseBuilder builder = new OperationResponseBuilder();
		
		OperationResult operationResult = builder.build(id, result);
		
		return JsonUtil.convertObjectToJsonBytes(operationResult);
	}
	
	public static byte[] createOperationRequestInJson(String id, double [][] operations) throws IOException {
		OperationRequest operationRequest = OperationRequestBuilder.build(id, operations);
		
		return JsonUtil.convertObjectToJsonBytes(operationRequest);
	}
}