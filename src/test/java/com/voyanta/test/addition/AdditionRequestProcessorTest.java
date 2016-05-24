package com.voyanta.test.addition;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.voyanta.test.entities.OperationRequest;
import com.voyanta.test.entities.OperationResult;
import com.voyanta.test.execution.OperationExecutor;
import com.voyanta.test.execution.OperationParallelExecutor;
import com.voyanta.test.util.AdditionResultAsserter;
import com.voyanta.test.util.OperationResponseBuilder;

public class AdditionRequestProcessorTest {
	String id;
	OperationRequest request;
	double [][] operations;
	double [] result;
	
	@Mock
	AdditionRequestValidator aditionRequestValidator;
	
	@Mock
	OperationExecutor additionExecutor;
	
	@Mock
	OperationParallelExecutor operationThreadExecutor;

	
	OperationResponseBuilder operationResponseBuilder = new OperationResponseBuilder();
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		initDefaultValues();
		
		initResponse();
	}

	private void initDefaultValues() {
		id = "1";
		operations = new double [0][0];
		result = new double [0];
	}
	
	@Test
	public void execute_requestPassed_requestIsValidated() throws Exception {
		//	given
		mockInjectedBeans();
		AdditionRequestProcessor processor = createAdditionRequestProcessor();
		
		//	when
		processor.execute(request);
		
		//	then
		verify(aditionRequestValidator).validate(request);
		
	}
	
	@Test(expected=Exception.class)
	public void execute_validationFails_exceptionThrown() throws Exception {
		//	given
		mockAditionRequestValidatorToFail();
		mockAdditionExecutor();
		mockOperationThreadBatchCreator();
		
		AdditionRequestProcessor processor = createAdditionRequestProcessor();
		
		//	when
		processor.execute(request);
	}	

	@Test
	public void execute_twoNumbersArePassed_SumIsReturned() throws Exception {
		//	given
		operations = new double [][] {{1.0, 2.0}, {3.0, 4.0}};
		result = new double [] {3.0, 7.0};
		request.setId(id);
		request.setOperations(operations);
		
		mockInjectedBeans();
		
		AdditionRequestProcessor processor = createAdditionRequestProcessor();
		
		//	when
		OperationResult returned = processor.execute(request);
		
		//	then
		AdditionResultAsserter.assertResultsAreCorrect(result, returned.getResult());
	}

	@Test
	public void execute_requestIsPassed_responseHasSameId() throws Exception {
		//	given
		id = "1";
		request.setId(id);
		
		mockInjectedBeans();
		
		AdditionRequestProcessor processor = createAdditionRequestProcessor();
		
		//	when
		OperationResult returned = processor.execute(request);
		
		//	then
		assertEquals(returned.getId(), request.getId());
		
	}
	
	@Test
	public void execute_alreadyExecutedOperation_cacheResultIsUse() {
		
	}

	private void mockInjectedBeans() throws Exception {
		mockAdditionRequestValidatorForSuccess();
		mockAdditionExecutor();
		mockOperationThreadBatchCreator();
	}

	private AdditionRequestProcessor createAdditionRequestProcessor() {
		AdditionRequestProcessor processor = new AdditionRequestProcessor();
		
		processor.setAditionRequestValidator(aditionRequestValidator);
		processor.setAdditionExecutor(additionExecutor);
		processor.setOperationThreadExecutor(operationThreadExecutor);
		processor.setOperationResponseBuilder(operationResponseBuilder);
		
		return processor;
	}
	
	private AdditionRequestValidator mockAdditionRequestValidatorForSuccess() {
		doNothing().when(aditionRequestValidator).validate(request);
		
		return aditionRequestValidator;
	}
	
	private AdditionRequestValidator mockAditionRequestValidatorToFail() {
		doThrow(new RuntimeException()).when(aditionRequestValidator).validate(request);
		
		return aditionRequestValidator;
	}
	
	private OperationExecutor mockAdditionExecutor() {
		for(int i = 0; i < result.length; i ++){
			doReturn(result[i]).when(additionExecutor).executeOperation(operations[i]);
		}
		
		return additionExecutor;
	}
	
	private OperationParallelExecutor mockOperationThreadBatchCreator() throws Exception {
		
		doReturn(result).when(operationThreadExecutor).execute(operations, additionExecutor);
		
		return operationThreadExecutor;
	}

	private void initResponse() {
		request = new OperationRequest();
		request.setOperations(operations);
	}
}
