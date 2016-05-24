package com.voyanta.test.data.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voyanta.test.util.OperationOrderedSerializer;

@Component
public class CacheEntryBuilder {
	
	@Autowired
	OperationOrderedSerializer operationOrderedSerializer;

	public CacheEntry build(double [] operands, double result){
		
		String flatOrderedOperands = operationOrderedSerializer.getFlatOrderedOperationAsString(operands);
		
		CacheEntry entry = build(flatOrderedOperands, result);
		
		return entry;
		
	}

	public CacheEntry build(String operands, double result) {
		CacheEntry entry = new CacheEntry();
		
		entry.setOperands(operands);
		entry.setResult(result);
		
		return entry;
	}
	
	public void setOperationOrderedSerializer(OperationOrderedSerializer operationOrderedSerializer) {
		this.operationOrderedSerializer = operationOrderedSerializer;
	}
}
