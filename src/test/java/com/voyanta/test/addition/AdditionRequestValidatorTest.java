package com.voyanta.test.addition;

import org.junit.Test;

import com.voyanta.test.addition.AdditionRequestValidator;
import com.voyanta.test.entities.OperationRequest;
import com.voyanta.test.util.OperationRequestBuilder;

public class AdditionRequestValidatorTest {
	
	@Test(expected=Exception.class)
	public void execute_emptyRequestId_exceptionThrown() {
		//	given
		String requestId = "";
		double [][] operands = {{1,2}};
		
		OperationRequest request = OperationRequestBuilder.build(requestId, operands);		
		AdditionRequestValidator validator = new AdditionRequestValidator();
		
		//	when
		validator.validate(request);
	}
	
	@Test(expected=Exception.class)
	public void execute_nullRequestId_exceptionThrown() {
		//	given
		String requestId = null;
		double [][] operands = {{1,2}};
		
		OperationRequest request = OperationRequestBuilder.build(requestId, operands);		
		AdditionRequestValidator validator = new AdditionRequestValidator();
		
		//	when
		validator.validate(request);
	}
	
	@Test(expected=Exception.class)
	public void execute_nullOperands_exceptionThrown() {
		//	given
		String requestId = "1";
		double [][] operands = null;
		
		OperationRequest request = OperationRequestBuilder.build(requestId, operands);		
		AdditionRequestValidator validator = new AdditionRequestValidator();
		
		//	when
		validator.validate(request);
	}
	
	@Test(expected=Exception.class)
	public void execute_emptyOperands_exceptionThrown() {
		//	given
		String requestId = "1";
		double [][] operands = new double[0][0];
		
		OperationRequest request = OperationRequestBuilder.build(requestId, operands);		
		AdditionRequestValidator validator = new AdditionRequestValidator();
		
		//	when
		validator.validate(request);
	}
	
	@Test(expected=Exception.class)
	public void execute_onlyOneOperand_exceptionThrown() {
		//	given
		String requestId = "1";
		double [][] operands = {{1}};
		
		OperationRequest request = OperationRequestBuilder.build(requestId, operands);		
		AdditionRequestValidator validator = new AdditionRequestValidator();
		
		//	when
		validator.validate(request);
	}
	
	@Test
	public void execute_multipleOperand_nothingHappens() {
		//	given
		String requestId = "1";
		double [][] operands = {{1, 2, 3}};
		
		OperationRequest request = OperationRequestBuilder.build(requestId, operands);		
		AdditionRequestValidator validator = new AdditionRequestValidator();
		
		//	when
		validator.validate(request);
	}
	
	@Test
	public void execute_twoOperand_nothingHappens() {
		//	given
		String requestId = "1";
		double [][] operands = {{1, 2}};
		
		OperationRequest request = OperationRequestBuilder.build(requestId, operands);		
		AdditionRequestValidator validator = new AdditionRequestValidator();
		
		//	when
		validator.validate(request);
	}
}
