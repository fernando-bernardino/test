package com.voyanta.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.voyanta.test.addition.AdditionRequestProcessor;
import com.voyanta.test.entities.OperationRequest;
import com.voyanta.test.entities.OperationResult;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LogFactory.class})
public class CreateAPIControllerTest {
	OperationResult resultDummy;
	OperationRequest requestDummy;
	
	@Mock
	Log loggerMock;
	
	@Before
	public void setup(){
		resultDummy = new OperationResult();
		requestDummy = new OperationRequest();
		
		MockitoAnnotations.initMocks(this);
		
		mockLog();
	}
	
	@Test
	public void add_executeThrowsException_httpBadRequestReturned() throws Exception {
		//	given
		AdditionRequestProcessor aditionProcessorMock = createAditionRequestProcessorForInvalidArgumentException();
		CreateAPIController controller = createCreateAPIController(aditionProcessorMock);
		
		//	when
		ResponseEntity<OperationResult> response = controller.add(requestDummy);
		
		//	then
		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
	}
	
	@Test
	public void add_executeSuccessful_httpSuccessReturned() throws Exception {
		AdditionRequestProcessor aditionProcessorMock = createAditionRequestProcessorForSuccess();
		CreateAPIController controller = createCreateAPIController(aditionProcessorMock);
		
		//	when
		ResponseEntity<OperationResult> response = controller.add(requestDummy);
		
		//	then
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void add_executeSuccessful_correctOperationResultReturned() throws Exception {
		//	given
		AdditionRequestProcessor aditionProcessorMock = createAditionRequestProcessorForSuccess();
		CreateAPIController controller = createCreateAPIController(aditionProcessorMock);
		
		//	when
		ResponseEntity<OperationResult> response = controller.add(requestDummy);
		
		//	then
		assertEquals(response.getBody(), resultDummy);
	}
	
	@Test
	public void add_someExceptionThrown_internalServerErrorReturned() throws Exception{
		//	given
		AdditionRequestProcessor aditionProcessorMock = createAditionRequestProcessorForGenericException();
		CreateAPIController controller = createCreateAPIController(aditionProcessorMock);
		
		//	when
		ResponseEntity<OperationResult> response = controller.add(requestDummy);
		
		//	then
		assertTrue(response.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR));
	}

	private CreateAPIController createCreateAPIController(AdditionRequestProcessor aditionProcessorMock) {
		CreateAPIController controller = new CreateAPIController();
		controller.setAditionProcessor(aditionProcessorMock);
		
		return controller;
	}

	private AdditionRequestProcessor createAditionRequestProcessorForInvalidArgumentException() throws Exception {
		AdditionRequestProcessor aditionProcessorMock = mock(AdditionRequestProcessor.class);
		
		doThrow(new IllegalArgumentException()).when(aditionProcessorMock).execute(requestDummy);
		
		return aditionProcessorMock;
	}
	

	private AdditionRequestProcessor createAditionRequestProcessorForGenericException() throws Exception {
		AdditionRequestProcessor aditionProcessorMock = mock(AdditionRequestProcessor.class);
		
		doThrow(new RuntimeException()).when(aditionProcessorMock).execute(requestDummy);
		
		return aditionProcessorMock;
	}
	
	private AdditionRequestProcessor createAditionRequestProcessorForSuccess() throws Exception {
		AdditionRequestProcessor aditionProcessorMock = mock(AdditionRequestProcessor.class);
		
		doReturn(resultDummy).when(aditionProcessorMock).execute(requestDummy);
		
		return aditionProcessorMock;
	}

	private void mockLog() {
		PowerMockito.mockStatic(LogFactory.class);
		PowerMockito.when(LogFactory.getLog(CreateAPIController.class))
				.thenReturn(loggerMock);
	}
}
