package com.voyanta.test.util;

import org.springframework.stereotype.Component;

import com.voyanta.test.entities.OperationResult;

@Component
public class OperationResponseBuilder {
	
	public OperationResult build(String id, double [] result) {
		
		OperationResult operationResult = new OperationResult();
		
		operationResult.setId(id);
		operationResult.setResult(result);
		
		return operationResult;
	}
}
