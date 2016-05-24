package com.voyanta.test.addition;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.voyanta.test.data.cache.CacheEntry;
import com.voyanta.test.data.cache.CacheEntryBuilder;
import com.voyanta.test.data.cache.CacheEntryService;
import com.voyanta.test.util.OperationOrderedSerializer;

public class AdditionCacheAwareExecutorTest {
	
	@Mock
	CacheEntryService cacheEntryServiceImplMock;
	
	@Mock
	OperationOrderedSerializer operationOrderedSerializerMock;
	
	@Mock
	AdditionExecutor additionExecutorMock;
	
	
	CacheEntryBuilder cacheEntryBuilder;
	
	private double [] operation = {1.0, 2.0};
	private String stringOperation = "1.0,2.0";
	double expectedResult = 3.0;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		initCacheBuilder();
		
		initGlobalValuesWithDefaultValues();

	}

	private void initGlobalValuesWithDefaultValues() {
		operation = new double [] {1.0, 2.0};
		stringOperation = "1.0,2.0";
		expectedResult = 3.0;
	}

	private void initCacheBuilder() {
		cacheEntryBuilder = new CacheEntryBuilder();
		cacheEntryBuilder.setOperationOrderedSerializer(operationOrderedSerializerMock);
	}

	@Test
	public void executeOperation_nonexistingCacheEntry_rightValueIsReturned(){
		//	given
		mockOperationOrderedSerializer();
		mockAdditionExecutor();
		
		AdditionCacheAwareExecutor executor = createAdditionCacheAwareExecutor();
		
		//	when
		double result = executor.executeOperation(operation);
		
		//	then
		assertEquals(expectedResult, result, 0.0);
	}

	@Test
	public void executeOperation_nonexistingCacheEntry_entryIsSavedRightOperands(){
		//	given
		mockOperationOrderedSerializer();
		mockAdditionExecutor();
		
		AdditionCacheAwareExecutor executor = createAdditionCacheAwareExecutor();
		
		//	when
		executor.executeOperation(operation);
		
		//	then
		assertEquals(stringOperation, getCacheEntitySaved().getOperands());
	}

	@Test
	public void executeOperation_nonexistingCacheEntry_entryIsSavedRightResult(){
		//	given
		mockOperationOrderedSerializer();
		mockAdditionExecutor();
		
		AdditionCacheAwareExecutor executor = createAdditionCacheAwareExecutor();
		
		//	when
		executor.executeOperation(operation);
		
		//	then
		assertEquals(expectedResult, getCacheEntitySaved().getResult(), 0.0);
	}

	@Test
	public void executeOperation_nonexistingCacheEntry_entryIsSaved(){
		//	given
		mockOperationOrderedSerializer();
		mockAdditionExecutor();
		
		AdditionCacheAwareExecutor executor = createAdditionCacheAwareExecutor();
		
		//	when
		executor.executeOperation(operation);
		
		//	then
		verify(cacheEntryServiceImplMock).save((CacheEntry) any());
	}

	@Test
	public void executeOperation_existingCacheEntryWithWrongAddResult_sameWrongAddResultIsReturned(){
		//	given
		expectedResult = 3.0;
		mockAdditionExecutor();
		
		expectedResult = 4.0;
		mockOperationOrderedSerializer();
		mockCacheEntry();
		
		AdditionCacheAwareExecutor executor = createAdditionCacheAwareExecutor();
		
		//	when
		double result = executor.executeOperation(operation);
		
		//	then
		assertEquals(expectedResult, result, 0.0);
	}
	
	private void mockOperationOrderedSerializer(){
		when(operationOrderedSerializerMock.getFlatOrderedOperationAsString(operation)).thenReturn(stringOperation);
	}
	
	private CacheEntry mockCacheEntry(){
		CacheEntry cacheEntry = cacheEntryBuilder.build(operation, expectedResult);
		
		when(cacheEntryServiceImplMock.findCacheEntryByOperands(stringOperation)).thenReturn(cacheEntry);
		
		return cacheEntry;
	}

	private void mockAdditionExecutor() {
		when(additionExecutorMock.executeOperation(operation)).thenReturn(expectedResult);
	}

	private AdditionCacheAwareExecutor createAdditionCacheAwareExecutor() {
		AdditionCacheAwareExecutor executor = new AdditionCacheAwareExecutor();
		
		executor.setCacheEntryBuilder(cacheEntryBuilder);
		executor.setCacheServiceImpl(cacheEntryServiceImplMock);
		executor.setOperationOrderedSerializer(operationOrderedSerializerMock);
		executor.setAdditionExecutor(additionExecutorMock);
		
		return executor;
	}

	private CacheEntry getCacheEntitySaved() {
		ArgumentCaptor<CacheEntry> argumentCaptor = ArgumentCaptor.forClass(CacheEntry.class);
		
		verify(cacheEntryServiceImplMock).save(argumentCaptor.capture());
		
		return argumentCaptor.getValue();
	}
}
