package com.voyanta.test.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OperationOrderedSerializerTest {
	@Test
	public void build_listOperandsGiven_operandsReturnedSortedAndFlat(){
		//	given
		double [] operation = {4.4, 1.1, 3.3, 2.2, 1.1};
		String expectedOperandRepresentation = "1.1,1.1,2.2,3.3,4.4";
		OperationOrderedSerializer serializer = new OperationOrderedSerializer();
		
		//	when
		String orderedStringRepresentation = serializer.getFlatOrderedOperationAsString(operation);
		
		//	then
		assertEquals(expectedOperandRepresentation, orderedStringRepresentation);
	}

	@Test
	public void build_multipleDigitOperand_operandsReturnedSortedAndFlat(){
		//	given
		double [] operation = {44.4, 1111.1, 3.3, 0.2, 11111.1};
		String expectedOperandRepresentation = "0.2,3.3,44.4,1111.1,11111.1";
		OperationOrderedSerializer serializer = new OperationOrderedSerializer();
		
		//	when
		String orderedStringRepresentation = serializer.getFlatOrderedOperationAsString(operation);
		
		//	then
		assertEquals(expectedOperandRepresentation, orderedStringRepresentation);
	}
}
