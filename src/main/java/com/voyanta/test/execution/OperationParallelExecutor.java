package com.voyanta.test.execution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.DoubleStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OperationParallelExecutor {
	@Value("${max.number.thread: 10}")
	private int maxNumberOfThreads;

	public double [] execute(double [][] operations, OperationExecutor executor) throws Exception {
		
		if(maxNumberOfThreads > 0){
			return executeParallel(operations, executor);	
			
		} else {
			return executeSingleThread(operations, executor);
		}
	}

	private double[] executeSingleThread(double[][] operations, OperationExecutor executor) {
		
		return mapToResult(
					Arrays.stream(operations).mapToDouble((operation) -> executor.executeOperation(operation)),
					operations.length);
		/*
		double [] results = new double[operations.length];
		
		IndexSupplier indexSupplier = new IndexSupplier();
		
		Arrays.stream(operations)
				.forEach((operation) -> 
					results[indexSupplier.getAsInt()] = executor.executeOperation(operation));
		*/
		/*
		IndexSupplier index = new IndexSupplier();
		
		IntStream.range(0, operations.length).forEach(i ->
				System.out.println("i " + i + " supplier" + index.getAsInt()));
		*/
		/*
		IntStream.range(0, operations.length).forEach(
				index -> results[index] = executor.executeOperation(operations[index])
		);
		
		return results;
		*/
	}

	private double [] mapToResult(DoubleStream stream, int size){
		double [] results = new double[size];
		
		IndexSupplier indexSupplier = new IndexSupplier();
		
		stream.forEach(d -> results[indexSupplier.getAsInt()] = d);
		
		return results;
	}

	private double[] executeParallel(double[][] operations, OperationExecutor executor)
			throws Exception {
		
		List<Callable<Double>> callableList = generateCallableList(operations, executor);
		
		List<Future<Double>> futureList = executeSynchronized(callableList);
		
		return getResults(futureList);
	}
	
	private List<Future<Double>> executeSynchronized(List<Callable<Double>> callableList)
			throws InterruptedException, ExecutionException {
		
		ExecutorService threadPool = Executors.newFixedThreadPool(maxNumberOfThreads);
		
		List<Future<Double>> futureList = threadPool.invokeAll(callableList);
		
		waitExecutionToEnd(threadPool);
		
		return futureList;
	}

	private double [] getResults(List<Future<Double>> futureList) throws Exception {
		return mapToResult(
				futureList.stream().mapToDouble(future -> getFutureValue(future)),
				futureList.size());
	}
	
	private Double getFutureValue(Future<Double> future){
		try {
			return future.get();
		} catch (Exception e) {}
		
		return null;
	}

	private void waitExecutionToEnd(ExecutorService threadPool) throws InterruptedException {
		threadPool.shutdown();
		threadPool.awaitTermination(1, TimeUnit.HOURS);
	}
	
	private List<Callable<Double>> generateCallableList(double[][] operations, OperationExecutor executor){
		List<Callable<Double>> callableList = new ArrayList<>();
		
		Arrays.stream(operations)
				.forEach(operation -> 
						callableList.add(new OperationExecutorCallable(operation, executor)));
		
		return callableList;
	}

	public int getMaxNumberOfThreads() {
		return maxNumberOfThreads;
	}

	public void setMaxNumberOfThreads(int maxNumberOfThreads) {
		this.maxNumberOfThreads = maxNumberOfThreads;
	}
}
