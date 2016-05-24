package com.voyanta.test;

import static com.voyanta.test.util.JsonUtil.createOperationRequestInJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.voyanta.test.data.cache.CacheEntry;
import com.voyanta.test.data.cache.CacheEntryBuilder;
import com.voyanta.test.data.cache.CacheEntryService;
import com.voyanta.test.util.JsonUtil;
import com.voyanta.test.util.OperationOrderedSerializer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
@TestPropertySource("file:config/db-test.properties")
@WebAppConfiguration
public class CreateAPIControllerCacheITTest {
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	CacheEntryService cacheServiceImpl;
	
	@Autowired
	CacheEntryBuilder cacheEntryBuilder;
	
	@Autowired
	OperationOrderedSerializer operationOrderedSerializer;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		clearChacheEntries();
		
		insertDummyCacheEntries();
		
		mockMvc();
	}
	
	@After
	public void tearDown() {
		clearChacheEntries();
	}
	
	@Test
	public void add_entryNotCached_entryIsSaved() throws Exception{
		
		//	given
		String requestId = "1";
		double [][] operands = {{1, 2}};
		double [] expectedResult = {3.0};
		
		//	when
		mockMvc.perform(post("/add")
				.contentType(JsonUtil.APPLICATION_JSON_UTF8)
				.content(createOperationRequestInJson(requestId, operands)))
				.andExpect(status().isOk());
		
		//	then
		assertCacheEntriesExists(operands, expectedResult);
		
	}
	
	@Test
	public void add_entryCachedWithWrongValue_wrongValueIsReturned() throws Exception{
		//	given
		
		String requestId = "1";
		double [][] operands = {{1, 2}};
		double [] expectedResult = {4.0};
		
		addCacheEntry(operands[0], 4.0);
		
		//	when
		mockMvc.perform(post("/add")
				.contentType(JsonUtil.APPLICATION_JSON_UTF8)
				.content(createOperationRequestInJson(requestId, operands)))
				.andExpect(status().isOk());
		
		//	then
		assertCacheEntriesExists(operands, expectedResult);
	}
	
	@Test
	public void add_sameOperationMultipleTimes_onlyOneCacheEntryIsCreated() throws Exception{
		//	given
		String requestId = "1";
		double [][] operands = {{1,2}, {1,2}, {1,2}, {1,2}, {1,2}, {1,2},
				{1,2}, {1,2}, {1,2}, {1,2}, {1,2}, {1,2},{1,2}, {1,2}, {1,2},
				{1,2}, {1,2}, {1,2}, {1,2}, {1,2}, {1,2},{1,2}, {1,2}, {1,2},
				{1,2}, {1,2}, {1,2}, {1,2}, {1,2}, {1,2},{1,2}, {1,2}, {1,2},
				{1,2}, {1,2}, {1,2}, {1,2}, {1,2}, {1,2},{1,2}, {1,2}, {1,2},
				{1,2}, {1,2}, {1,2}, {1,2}, {1,2}, {1,2},{1,2}, {1,2}, {1,2}};
		
		//	when
		mockMvc.perform(post("/add")
				.contentType(JsonUtil.APPLICATION_JSON_UTF8)
				.content(createOperationRequestInJson(requestId, operands)))
				.andExpect(status().isOk());
		
		//	then
		assertCacheEntryExists(operationOrderedSerializer.getFlatOrderedOperationAsString(
				operands[0]), 3.0);
		
		//	multiple entries in the cache cause HibernateException while using Criteria.uniqueResult()
		//	not throwing an exception is proof enough one entry exists at most in the cache,
		//	assert ensures it exists at least one
	}

	private void assertCacheEntriesExists(double[][] operands, double[] expectedResult) {
		
		for(int i = 0; i < operands.length; i ++){
			assertCacheEntryExists(
					operationOrderedSerializer.getFlatOrderedOperationAsString(operands[i]), 
					expectedResult[i]);
		}
	}

	private void assertCacheEntryExists(String operands, double result) {
		CacheEntry cacheEntry = cacheServiceImpl.findCacheEntryByOperands(operands);
		
		assertNotNull(cacheEntry);
		assertEquals(result, cacheEntry.getResult(), 0.0000001);
	}

	private void addCacheEntry(double [] operands, double result) {
		cacheServiceImpl.save(cacheEntryBuilder.build(operands, result));
	}

	private void clearChacheEntries() {
		cacheServiceImpl.clearCacheEntries();
	}

	private void insertDummyCacheEntries() {
		addCacheEntry(new double [] {1,1}, 2.0);
		addCacheEntry(new double [] {2,2}, 4.0);
	}
	
	private void mockMvc() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();
	}
}
