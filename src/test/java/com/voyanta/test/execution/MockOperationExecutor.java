package com.voyanta.test.execution;

/**
 * Mocks {@link com.voyanta.test.execution.OperationExecutor} performing the following functions:<br/><ul><li>
 * 
 * 		Counts how many times {@link #executeOperation(double[])} is called</li><li>
 * 
 * 		Short circuits the first value of the array passed to {@link #executeOperation(double[])}
 * 
 */
public class MockOperationExecutor implements OperationExecutor {
	
	private Object lock = new Object();
	
	private int numberExecutions = 0;

	/**
	 * Short circuits the value passed in the first position {@link operation}
	 */
	@Override
	public double executeOperation(double[] operations) {
		synchronized (lock) {
			numberExecutions ++;
		}
		
		return operations[0];
	}
	
	/**
	 * Returns the number of times {@link #executeOperation(double[])} as been called for the current
	 * instance.
	 * 
	 *  @return number of executions
	 */
	public int getNumberExecution() {
		return numberExecutions;
	}
}
