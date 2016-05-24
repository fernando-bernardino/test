package com.voyanta.test.execution;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OperationParallelExecutorTest {
	@Test
	public void executeInParallel_multipleBatch_valuesAreCorrectlySet() throws Exception{
		//	given
		int numberOperations = 33;
		double [][] operations = produceDummyOperations(numberOperations);
		MockOperationExecutor executor = new MockOperationExecutor();
		OperationParallelExecutor creator = createOperationThreadBatchCreator();
		
		//	when
		double [] results = creator.execute(operations, executor);
		
		//	then
		assertValuesAreInPlace(operations, results);
	}

	@Test
	public void executeInParallel_multipleBatch_executedRightNumberOfTimes() throws Exception{
		//	given
		int numberOperations = 33;
		double [][] operations = produceDummyOperations(numberOperations);
		MockOperationExecutor executor = new MockOperationExecutor();
		OperationParallelExecutor creator = createOperationThreadBatchCreator();
		
		//	when
		creator.execute(operations, executor);
		
		//	then
		assertEquals(numberOperations, executor.getNumberExecution());		
	}
	
	@Test
	public void executeInParallel_singleBatch_valuesAreCorrectlySet() throws Exception{
		//	given
		int numberOperations = 5;
		double [][] operations = produceDummyOperations(numberOperations);
		MockOperationExecutor executor = new MockOperationExecutor();
		OperationParallelExecutor creator = createOperationThreadBatchCreator();
		
		//	when
		double [] results = creator.execute(operations, executor);
		
		//	then
		assertValuesAreInPlace(operations, results);
	}
	
	@Test
	public void executeInParallel_singleBatch_executedRightNumberOfTimes() throws Exception{
		//	given
		int numberOperations = 5;
		double [][] operations = produceDummyOperations(numberOperations);
		MockOperationExecutor executor = new MockOperationExecutor();
		OperationParallelExecutor creator = createOperationThreadBatchCreator();
		
		//	when
		creator.execute(operations, executor);
		
		//	then
		assertEquals(numberOperations, executor.getNumberExecution());				
	}
	
	@Test
	public void executeInParallel_infiniteBatchSize_executedOnlyOneBatch() throws Exception{
		//	given
		int numberOperations = 100;
		double [][] operations = produceDummyOperations(numberOperations);
		ThreadCounterOperationExecutor executor = new ThreadCounterOperationExecutor();
		OperationParallelExecutor creator = createOperationParallelExecutorToBeSingleThread();
		
		//	when
		creator.execute(operations, executor);
		
		//	then
		assertEquals(1, executor.getNumberOfThreadUsed());				
	}

	private OperationParallelExecutor createOperationParallelExecutorToBeSingleThread() {
		OperationParallelExecutor creator = createOperationThreadBatchCreator();
		creator.setMaxNumberOfThreads(-1);
		return creator;
	}

	private OperationParallelExecutor createOperationThreadBatchCreator() {
		OperationParallelExecutor creator = new OperationParallelExecutor();
		
		creator.setMaxNumberOfThreads(5);
		
		return creator;
	}
	
	private double [][] produceDummyOperations(int size) {
		double [][] operations = new double [size][2];
		
		for(int i = 0; i < size; i ++){
			operations[i][0] = i;
			operations[i][0] = i;
		}
		
		return operations;
	}
	
	private void assertValuesAreInPlace(double[][] operations, double[] results) {
		for(int i = 0; i < operations.length; i ++) {
			assertEquals(operations[i][0], results[i], 0.0);
		}
	}
}
