package com.voyanta.test.data.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.voyanta.test.data.cache.CacheEntry;
import com.voyanta.test.data.cache.CacheEntryBuilder;
import com.voyanta.test.util.OperationOrderedSerializer;

public class CacheEntryTest {
	CacheEntryBuilder builder;
	
	@Before
	public void setup(){
		builder = new CacheEntryBuilder();
		builder.setOperationOrderedSerializer(new OperationOrderedSerializer());
	}
	@Test
	public void hashCode_objectWithSameOperations_returnTrue() {
		//	given
		double [] operands1 = {1.111, 2.222};
		double [] operands2 = {1.111, 2.222};
		double result = 3.333;
		
		CacheEntry entry1 = builder.build(operands1, result);
		CacheEntry entry2 = builder.build(operands2, result);
		
		//	then
		assertEquals(entry1.hashCode(), entry2.hashCode());
	}
	
	@Test
	public void hashCode_objectDifferentOperations_returnFalse() {
		//	given
		double [] operands1 = {1.111, 2.222};
		double [] operands2 = {1.11, 2.222};
		double result = 3.333;
		
		CacheEntry entry1 = builder.build(operands1, result);
		CacheEntry entry2 = builder.build(operands2, result);
		
		//	then
		assertNotEquals(entry1.hashCode(), entry2.hashCode());
	}
	
	@Test
	public void equals_objectWithSameOperations_returnTrue() {
		//	given
		double [] operands1 = {1.111, 2.222};
		double [] operands2 = {1.111, 2.222};
		double result = 3.333;
		
		CacheEntry entry1 = builder.build(operands1, result);
		CacheEntry entry2 = builder.build(operands2, result);
		
		//	then
		assertTrue(entry1.equals(entry2));
	}
	
	@Test
	public void equals_objectWithDifferentOperations_returnsFalse() {
		//	given
		double [] operands1 = {1.111, 2.222};
		double [] operands2 = {1.11, 2.222};
		double result = 3.333;
		
		CacheEntry entry1 = builder.build(operands1, result);
		CacheEntry entry2 = builder.build(operands2, result);
		
		//	then
		assertFalse(entry1.equals(entry2));
	}
}
