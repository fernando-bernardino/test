package com.voyanta.test.util;

import com.voyanta.test.entities.OperationRequest;

public class OperationRequestBuilder {
	
	public static OperationRequest build(String id, double[][] operands) {
		OperationRequest operation = new OperationRequest();
		
		operation.setId(id);
		operation.setOperations(operands);
		
		return operation;
	}
}
