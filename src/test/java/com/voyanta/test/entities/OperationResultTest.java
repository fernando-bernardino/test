package com.voyanta.test.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.voyanta.test.util.OperationResponseBuilder;

public class OperationResultTest {
	OperationResponseBuilder operationResponseBuilder = new OperationResponseBuilder();
	@Test
	public void hashCode_objectWithSameIds_returnTrue() {
		//	given
		String id = "1";
		double [] result = {1.111};
		
		OperationResult response1 = operationResponseBuilder.build(id, result);
		OperationResult response2 = operationResponseBuilder.build(id, result);
		
		//	then
		assertEquals(response1.hashCode(), response2.hashCode());
	}
	
	@Test
	public void hashCode_objectDifferentIds_returnFalse() {
		//	given
		String id1 = "1";
		String id2 = "2";
		double [] result = {1.111};
		
		OperationResult response1 = operationResponseBuilder.build(id1, result);
		OperationResult response2 = operationResponseBuilder.build(id2, result);
		
		//	then
		assertNotEquals(response1.hashCode(), response2.hashCode());
	}
	
	@Test
	public void equals_sameObject_returnsTrue() {
		//	given
		String id = "1";
		double [] result = {1.111};
		
		OperationResult response = operationResponseBuilder.build(id, result);
		
		//	then
		assertTrue(response.equals(response));
	}
	
	@Test
	public void equals_nullObject_returnsFalse() {
		//	given
		String id = "1";
		double [] result = {1.111};
		
		OperationResult response = operationResponseBuilder.build(id, result);
		
		//	then
		assertFalse(response.equals(null));
	}
	
	@Test
	public void equals_objectWithDifferentId_returnsFalse() {
		//	given
		String id1 = "1";
		String id2 = "2";
		double [] result = {1.111};
		
		OperationResult response1 = operationResponseBuilder.build(id1, result);
		OperationResult response2 = operationResponseBuilder.build(id2, result);
		
		//	then
		assertFalse(response1.equals(response2));
	}
}
