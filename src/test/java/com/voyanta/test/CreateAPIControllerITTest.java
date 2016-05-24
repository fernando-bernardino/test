package com.voyanta.test;

import static com.voyanta.test.util.JsonUtil.convertJsonBytesToObject;
import static com.voyanta.test.util.JsonUtil.createOperationRequestInJson;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.voyanta.test.entities.OperationResult;
import com.voyanta.test.util.AdditionResultAsserter;
import com.voyanta.test.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
@TestPropertySource("file:config/db-test.properties")
@WebAppConfiguration
public class CreateAPIControllerITTest {
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();
	}

	@Test
	public void add_twoNumbersPassed_sumIsReturned() throws Exception {
		//	given
		String requestId = "1";
		double [][] operands = {{1.11111, 2.22222}};
		double [] expectedResult = {3.33333};
		
		//	when
		MvcResult response = mockMvc.perform(post("/add")
				.contentType(JsonUtil.APPLICATION_JSON_UTF8)
				.content(createOperationRequestInJson(requestId, operands)))
				.andReturn();
		
		//	then
		AdditionResultAsserter.assertResultsAreCorrect(expectedResult, getResultsFromResponse(response));
	}

	@Test
	public void add_multipleNumbersPassed_sumIsReturned() throws Exception {
		//	given
		String requestId = "1";
		double [][] operands = {{1.11111, 2.22222, 3.33333, 4.44444, 5.55555}};
		double [] expectedResult = {16.66665};
		
		//	when
		MvcResult response = mockMvc.perform(post("/add")
				.contentType(JsonUtil.APPLICATION_JSON_UTF8)
				.content(createOperationRequestInJson(requestId, operands)))
				.andReturn();
		
		//	then
		AdditionResultAsserter.assertResultsAreCorrect(expectedResult, getResultsFromResponse(response));
	}
	
	@Test
	public void add_requestWithId_responseReturnsSameId() throws Exception {
		//	given
		String requestId = "1";
		double [][] operands = {{1, 2}};
		
		//	when
		MvcResult response = mockMvc.perform(post("/add")
				.contentType(JsonUtil.APPLICATION_JSON_UTF8)
				.content(createOperationRequestInJson(requestId, operands)))
				.andReturn();
		
		//	then
		OperationResult result = getOperationResultFromJson(response);
		assertEquals(requestId, result.getId());
	}
	
	@Test
	public void add_norequestId_badRequestResponse() throws Exception {
		//	given
		String requestId = "";
		double [][] operands = {{1, 2},{3,4}};
		
		//	when
		mockMvc.perform(post("/add")
				.contentType(JsonUtil.APPLICATION_JSON_UTF8)
				.content(createOperationRequestInJson(requestId, operands)))
		
		//	then
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void add_noOperands_badRequestResponse() throws Exception {
		//	given
		String requestId = "1";
		double [][] operands = {};
		
		//	when
		mockMvc.perform(post("/add")
				.contentType(JsonUtil.APPLICATION_JSON_UTF8)
				.content(createOperationRequestInJson(requestId, operands)))
		
		//	then
			.andExpect(status().isBadRequest());
		
	}

	private OperationResult getOperationResultFromJson(MvcResult response)
			throws UnsupportedEncodingException, IOException {
		
		byte [] content = response.getResponse().getContentAsString().getBytes();
		OperationResult result = (OperationResult) convertJsonBytesToObject(content, OperationResult.class);
		
		return result;
	}
	
	@Test
	public void add_multipleOperations_resultsCorrect() throws Exception {
		//	given
		String requestId = "1";
		double [][] operands = {
				{1, 1},
				{2, 2},
				{3, 3},
				{4, 4},
				{5, 5},
				{6, 6},
				{7, 7},
				{8, 8},
				{9, 9},
				{10, 10},
				{11, 11},
				{12, 12},
				{13, 13},
				{14, 14}};
		
		double [] expectedResult = {
				2,
				4,
				6,
				8,
				10,
				12,
				14,
				16,
				18,
				20,
				22,
				24,
				26,
				28};
		
		
		//	when
		MvcResult response = mockMvc.perform(post("/add")
				.contentType(JsonUtil.APPLICATION_JSON_UTF8)
				.content(createOperationRequestInJson(requestId, operands)))
				.andReturn();
		
		//	then
		AdditionResultAsserter.assertResultsAreCorrect(expectedResult, getResultsFromResponse(response));
	}

	private double[] getResultsFromResponse(MvcResult response) throws UnsupportedEncodingException, IOException {
		OperationResult operationResponse = getOperationResultFromJson(response);
		double [] actualResult = operationResponse.getResult();
		return actualResult;
	}
}
