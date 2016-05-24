package com.voyanta.test.addition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voyanta.test.entities.OperationRequest;
import com.voyanta.test.entities.OperationResult;
import com.voyanta.test.execution.OperationExecutor;
import com.voyanta.test.execution.OperationParallelExecutor;
import com.voyanta.test.util.OperationResponseBuilder;

@Component
public class AdditionRequestProcessor {
	
	@Autowired
	AdditionRequestValidator aditionRequestValidator;
	
	@Autowired
	OperationExecutor additionCacheAwareExecutor;
	
	@Autowired
	OperationParallelExecutor operationParallelExecutor;

	@Autowired
	OperationResponseBuilder operationResponseBuilder;
	
	
	public OperationResult execute(OperationRequest operation) throws Exception {
		
		aditionRequestValidator.validate(operation);
		
		double [] results = operationParallelExecutor.execute(operation.getOperations(), additionCacheAwareExecutor);
		
		return operationResponseBuilder.build(operation.getId(), results);
	}

	public void setAditionRequestValidator(AdditionRequestValidator aditionRequestValidator) {
		this.aditionRequestValidator = aditionRequestValidator;
	}

	public void setAdditionExecutor(OperationExecutor additionExecutor) {
		this.additionCacheAwareExecutor = additionExecutor;
	}
	
	public void setOperationThreadExecutor(OperationParallelExecutor operationThreadExecutor) {
		this.operationParallelExecutor = operationThreadExecutor;
	}

	public void setOperationResponseBuilder(OperationResponseBuilder operationResponseBuilder) {
		this.operationResponseBuilder = operationResponseBuilder;
	}
}
