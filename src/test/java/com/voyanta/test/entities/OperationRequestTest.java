package com.voyanta.test.entities;

import static com.voyanta.test.util.OperationRequestBuilder.build;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class OperationRequestTest {
	
	@Test
	public void hashCode_objectDifferentIds_returnFalse() {
		//	given
		String id1 = "1";
		String id2 = "2";
		double [][] operands = {{1.111, 2.222}};
		
		OperationRequest request1 = build(id1, operands);
		OperationRequest request2 = build(id2, operands);
		
		//	then
		assertNotEquals(request1.hashCode(), request2.hashCode());
	}
	
	@Test
	public void hashCode_objectSameId_returnTrue() {
		//	given
		String id = "1";
		double [][] operands1 = {{1.111, 2.222}};
		double [][] operands2 = {{1.11, 2.222}};
		
		OperationRequest request1 = build(id, operands1);
		OperationRequest request2 = build(id, operands2);
		
		//	then
		assertEquals(request1.hashCode(), request2.hashCode());
	}
	
	@Test
	public void equals_sameObject_returnsTrue() {
		//	given
		String id = "1";
		double [][] operands = {{1.111, 2.222}};
		
		OperationRequest request1 = build(id, operands);
		
		//	then
		assertTrue(request1.equals(request1));
	}
	
	@Test
	public void equals_objectWithSameIds_returnsTrue() {
		//	given
		String id = "1";
		double [][] operands = {{1.111, 2.222}};
		
		OperationRequest request1 = build(id, operands);
		OperationRequest request2 = build(id, operands);
		
		//	then
		assertTrue(request1.equals(request2));
	}
	
	@Test
	public void equals_objectWithDifferentId_returnsFalse() {
		//	given
		String id1 = "1";
		String id2 = "2";
		double [][] operands = {{1.111, 2.222}};
		
		OperationRequest request1 = build(id1, operands);
		OperationRequest request2 = build(id2, operands);
		
		//	then
		assertFalse(request1.equals(request2));
	}
	
	@Test
	public void equals_withNull_returnsFalse() {
		//	given
		String id = "1";
		double [][] operands = {{1.111, 2.222}};
		
		OperationRequest request = build(id, operands);
		
		//	then
		assertFalse(request.equals(null));
	}
}
