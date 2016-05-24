package com.voyanta.test.addition;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.voyanta.test.execution.OperationExecutor;

@Component
public class AdditionExecutor implements OperationExecutor {

	@Override
	public double executeOperation(double[] operation) {
		return Arrays.stream(operation).sum();
	}
}
