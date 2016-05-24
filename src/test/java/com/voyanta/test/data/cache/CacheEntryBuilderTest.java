package com.voyanta.test.data.cache;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.voyanta.test.data.cache.CacheEntry;
import com.voyanta.test.data.cache.CacheEntryBuilder;
import com.voyanta.test.util.OperationOrderedSerializer;

public class CacheEntryBuilderTest {
	
	@Mock
	OperationOrderedSerializer operationOrderedSerializer;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void build_operandStringGiven_operandsStringReturned(){
		//	given
		double result = 11.0;
		String expectedOperandList = "1.1,1.1,2.2,3.3,4.4";
		
		CacheEntryBuilder builder = createCacheEntryBuilder();
		
		//	when
		CacheEntry entry = builder.build(expectedOperandList, result);
		
		//	then
		assertEquals(expectedOperandList, entry.getOperands());
	}
	
	@Test
	public void build_operandStringGiven_resultReturned(){
		//	given
		double result = 11.0;
		String expectedOperandList = "1.1,1.1,2.2,3.3,4.4";
		
		CacheEntryBuilder builder = createCacheEntryBuilder();
		
		//	when
		CacheEntry entry = builder.build(expectedOperandList, result);
		
		//	then
		assertEquals(result, entry.getResult(), 0.0);
	}
	
	@Test
	public void build_operandsArrayGiven_operandsReturnedSortedAndFlat(){
		//	given
		double [] operation = {4.4, 1.1, 3.3, 2.2, 1.1};
		double result = 11.0;
		String expectedOperandList = "1.1,1.1,2.2,3.3,4.4";
		mockOperationOrderedSerializer(operation, expectedOperandList);
		
		CacheEntryBuilder builder = createCacheEntryBuilder();
		
		//	when
		CacheEntry entry = builder.build(operation, result);
		
		//	then
		assertEquals(expectedOperandList, entry.getOperands());
	}
	
	@Test
	public void build_operandsArrayGiven_resultCorrectlyPlaced(){
		//	given
		double [] operation = {4.4, 1.1, 3.3, 2.2, 1.1};
		double result = 11.0;
		CacheEntryBuilder builder = createCacheEntryBuilder();
		
		//	when
		CacheEntry entry = builder.build(operation, result);
		
		//	then
		assertEquals(result, entry.getResult(), 0.0);
	}

	private CacheEntryBuilder createCacheEntryBuilder() {
		CacheEntryBuilder builder = new CacheEntryBuilder();
		
		builder.setOperationOrderedSerializer(operationOrderedSerializer);
		
		return builder;
	}
	
	private void mockOperationOrderedSerializer(double [] operands, String expectedOperandList){
		when(operationOrderedSerializer.getFlatOrderedOperationAsString(operands)).thenReturn(expectedOperandList);
	}
}
