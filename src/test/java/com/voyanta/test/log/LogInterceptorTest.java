package com.voyanta.test.log;

import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LogFactory.class, System.class})
public class LogInterceptorTest {
	
	@Mock
	Log loggerMock;
	
	@Mock
	HttpServletRequest requestMock;
	
	@Mock
	HttpServletResponse responseMock;
	
	@Mock
	Object handlerMock;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		mockLog();
	}
	
	@Test
	@Ignore
	public void preHandle_called_setsRightStartTime() throws Exception{
		//	TODO - powermock not working
		//	given
		long systemTime = 1000L;
		mockSystemTime(systemTime);
		
		LogInterceptor logInterceptor = new LogInterceptor();
		
		//	when
		logInterceptor.preHandle(requestMock, responseMock, handlerMock);
		
		//	then
		Mockito.verify(requestMock).setAttribute("startTime", systemTime);

	}
	
	@Test
	@Ignore
	public void postHandle_called_logsRightTime() throws Exception{
		//	TODO - powermock not working
		
		//	given
		long startTime = 1000L;
		long endTime = 2000L;
		long totalTime = endTime - startTime;
		mockSystemTime(endTime);
		LogInterceptor logInterceptor = new LogInterceptor();
		
		Mockito.when(requestMock.getAttribute("startTime")).thenReturn(startTime);
		
		//	when
		logInterceptor.postHandle(requestMock, responseMock, handlerMock, null);
		
		//	then
		
		String logEntry = getLogEntry();
		assertTrue(logEntry.contains(" " + totalTime + "ms"));
	}
	@Test
	@Ignore
	public void postHandle_called_logsRightStatusCode() throws Exception{
		//	TODO - powermock not working
		//	given
		int httpStatusCode = HttpStatus.BAD_REQUEST.value();
		LogInterceptor logInterceptor = new LogInterceptor();
		
		Mockito.when(requestMock.getAttribute("startTime")).thenReturn(0L);
		
		Mockito.when(responseMock.getStatus()).thenReturn(httpStatusCode);
		
		//	when
		logInterceptor.postHandle(requestMock, responseMock, handlerMock, null);
		
		//	then
		String logEntry = getLogEntry();
		assertTrue(logEntry.contains(String.valueOf(httpStatusCode)));
	}

	private String getLogEntry() {
		ArgumentCaptor<Object> argumentCaptor = ArgumentCaptor.forClass(Object.class);
		
		Mockito.verify(loggerMock).debug(argumentCaptor.capture());
		
		return argumentCaptor.getValue().toString();
	}
	
	private void mockSystemTime(long systemTime) {
		PowerMockito.mockStatic(System.class);
		PowerMockito.when(System.currentTimeMillis())
				.thenReturn(systemTime);
	}

	private void mockLog() {
		PowerMockito.mockStatic(LogFactory.class);
		PowerMockito.when(LogFactory.getLog(LogInterceptor.class))
				.thenReturn(loggerMock);
	}

}
