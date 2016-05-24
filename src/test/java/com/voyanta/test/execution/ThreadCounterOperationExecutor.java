package com.voyanta.test.execution;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 *
 */
public class ThreadCounterOperationExecutor implements OperationExecutor {
	Set<Thread> threadsUsedToExecute = new HashSet<>();

	@Override
	public double executeOperation(double[] operations) {
		synchronized (threadsUsedToExecute) {
			threadsUsedToExecute.add(Thread.currentThread());
		}

		return operations[0];
	}
	
	public int getNumberOfThreadUsed() {
		return threadsUsedToExecute.size();
	}
}
