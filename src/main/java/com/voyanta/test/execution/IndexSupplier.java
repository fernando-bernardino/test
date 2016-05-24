package com.voyanta.test.execution;

import java.util.function.IntSupplier;

public class IndexSupplier implements IntSupplier {
	int index = 0;

	@Override
	public int getAsInt() {
		return index ++;
	}
}
