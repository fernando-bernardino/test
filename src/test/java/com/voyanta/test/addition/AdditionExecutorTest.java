package com.voyanta.test.addition;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class AdditionExecutorTest {
	AdditionExecutor executor;
	
	@Before
	public void setup(){
		executor = new AdditionExecutor();
	}
	
	@Test
	public void execute_twoValues_additionIsReturned() throws Exception{
		double [] operation = new double [2];
		operation[0] = 1;
		operation[1] = 2;
		
		double result = executor.executeOperation(operation);
		
		assertTrue(result == 3);
	}
	
	@Test
	public void execute_multiplesValues_sumIsReturned(){
		double [] operation = {1, 2, 3, 4, 5};
		
		double result = executor.executeOperation(operation);
		
		assertTrue(result == 15);
	}
}
