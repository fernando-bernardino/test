package com.voyanta.test.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.voyanta.test.entities.OperationResult;
import com.voyanta.test.util.OperationResponseBuilder;

public class OperationResponseBuilderTest {
	
	@Test
	public void build_passId_idIsSet(){
		//	given
		String id = "1";
		double [] result = {1.0, 2.0};
		OperationResponseBuilder builder = new OperationResponseBuilder();
		
		//	when
		OperationResult operationResult = builder.build(id, result);
		
		//	then
		assertEquals(operationResult.getId(), id);
	}
	
	@Test
	public void build_passResult_resultIsSet() {
		//	given
		String id = "1";
		double [] result = {1.0, 2.0};
		OperationResponseBuilder builder = new OperationResponseBuilder();
		
		//	when
		OperationResult operationResult = builder.build(id, result);
		
		//	then
		double [] actualResult = operationResult.getResult();
		assertResultIsEqual(result, actualResult);
	}

	private void assertResultIsEqual(double[] expectedResult, double [] actualResult) {
		for (int i = 0; i < actualResult.length; i ++) {
			assertEquals(expectedResult[i], actualResult[i], 0.0);
		}
	}
}
