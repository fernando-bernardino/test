package com.voyanta.test.addition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.voyanta.test.data.cache.CacheEntry;
import com.voyanta.test.data.cache.CacheEntryBuilder;
import com.voyanta.test.data.cache.CacheEntryService;
import com.voyanta.test.execution.OperationExecutor;
import com.voyanta.test.util.OperationOrderedSerializer;

@Component
public class AdditionCacheAwareExecutor implements OperationExecutor {
	
	@Autowired
	CacheEntryService cacheServiceImpl;

	@Autowired
	OperationOrderedSerializer operationOrderedSerializer;
	
	@Autowired
	CacheEntryBuilder cacheEntryBuilder;
	
	@Autowired
	AdditionExecutor additionExecutor;

	public double executeOperation(double[] operation) {
		String flatOrderedRepresentation = operationOrderedSerializer.getFlatOrderedOperationAsString(operation);
		
		CacheEntry entry = getCacheEntry(flatOrderedRepresentation);
		
		double result;
		
		if (entry == null) {
			result = additionExecutor.executeOperation(operation);
			
			saveNewCacheEntry(flatOrderedRepresentation, result);
			
		} else {
			result = entry.getResult();
		}
		
		return result;
	}

	private synchronized CacheEntry getCacheEntry(String operands) {
		return cacheServiceImpl.findCacheEntryByOperands(operands);
	}

	private synchronized void saveNewCacheEntry(String operands, double result) {
		
		if(getCacheEntry(operands) == null) {
			
			CacheEntry entry = cacheEntryBuilder.build(operands, result);
			
			cacheServiceImpl.save(entry);
		}
	}

	public void setCacheServiceImpl(CacheEntryService cacheServiceImpl) {
		this.cacheServiceImpl = cacheServiceImpl;
	}

	public void setOperationOrderedSerializer(OperationOrderedSerializer operationOrderedSerializer) {
		this.operationOrderedSerializer = operationOrderedSerializer;
	}

	public void setCacheEntryBuilder(CacheEntryBuilder cacheEntryBuilder) {
		this.cacheEntryBuilder = cacheEntryBuilder;
	}

	public void setAdditionExecutor(AdditionExecutor additionExecutor) {
		this.additionExecutor = additionExecutor;
	}
}
