package com.voyanta.test.execution;

import java.util.concurrent.Callable;

public class OperationExecutorCallable implements Callable<Double> {
	
	final private double [] operation;
	
	final private OperationExecutor executor;
	
	public OperationExecutorCallable(double [] operation, OperationExecutor executor){
		this.operation = operation;
		this.executor = executor;
	}

	@Override
	public Double call() throws Exception {
		return executor.executeOperation(operation);
	}
}
