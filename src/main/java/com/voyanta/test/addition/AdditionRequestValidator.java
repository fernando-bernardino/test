package com.voyanta.test.addition;

import java.util.Arrays;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.voyanta.test.entities.OperationRequest;

@Component
public class AdditionRequestValidator {
	
	public void validate(OperationRequest request) {
		validateRequestId(request);
		
		validateOperations(request);
	}

	private void validateRequestId(OperationRequest request) {
		if(ObjectUtils.isEmpty(request.getId())) {
			throw new IllegalArgumentException("Empty request id");
		}
	}
	
	private void validateOperations(OperationRequest request) {
		double [][] operations = request.getOperations();
		
		validateOperationsAreNotEmpty(operations);
		
		validateOperandSize(operations);
	}

	private void validateOperationsAreNotEmpty(double[][] operations) {
		if(operations == null || operations.length == 0) {
			throw new IllegalArgumentException("No operations");
		}
	}
	
	private void validateOperandSize(double [][] operations) {
		
		if(Arrays.stream(operations)
				.filter((operation) -> operation.length < 2)
				.count() > 0){
			
			throw  new IllegalArgumentException("Invalid number of operands");
		}
	}
}
