package com.voyanta.test.data.cache;

public interface CacheEntryDao {
	
	public void save(CacheEntry entry);
	
	public CacheEntry findCacheEntryByOperands(String operands);
	
	public void clearCacheEntries();
}
