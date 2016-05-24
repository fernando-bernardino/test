package com.voyanta.test.execution;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.voyanta.test.execution.OperationExecutorCallable;

public class OperationExecutorCallableTest {
	@Mock
	OperationExecutor executorDummy;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void newOperationBatchExecutor_executorAndOperationPassed_executorIsUsedWithOperation() throws Exception {
		//	given
		double [] operation = {1.1, 2.2};
		double result = 3.3;
		
		mockOperationExecutor(operation, result);

		OperationExecutorCallable singleOperationExecutor = new OperationExecutorCallable(operation, executorDummy);
		
		//	when
		singleOperationExecutor.call();
		
		//	then
		verify(executorDummy).executeOperation(operation);
	}
	
	@Test
	public void newOperationBatchExecutor_executorAndOperationPassed_correctResultIsReturned() throws Exception {
		//	given
		double [] operation = {1.1, 2.2};
		double result = 3.3;
		
		mockOperationExecutor(operation, result);

		OperationExecutorCallable singleOperationExecutor = new OperationExecutorCallable(operation, executorDummy);
		
		//	when
		double returnedResult = singleOperationExecutor.call();
		
		//	then
		assertEquals(result, returnedResult, 0.0);
	}

	private void mockOperationExecutor(double[] operation, double result) {
		Mockito.when(executorDummy.executeOperation(operation)).thenReturn(result);
	}
}
