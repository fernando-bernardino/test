package com.voyanta.test.util;

import static org.junit.Assert.assertEquals;

public class AdditionResultAsserter {

	public static void assertResultsAreCorrect(double[] expectedResult, double[] actualResult) throws Exception {
		double reallySmallDifference = 0.00001;
		
		assertEquals(expectedResult.length, actualResult.length);
		
		for(int i = 0; i < expectedResult.length; i ++) {
			assertEquals(expectedResult[i], actualResult[i], reallySmallDifference);
		}
	}
}
