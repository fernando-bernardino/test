package com.voyanta.test.util;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class OperationOrderedSerializer {

	public String getFlatOrderedOperationAsString(double[] operands) {
		
		return Arrays.stream(operands)
				.sorted()
				.mapToObj(String::valueOf)
				.collect(Collectors.joining(","));
	}
}
