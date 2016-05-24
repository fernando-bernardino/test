package com.voyanta.test.data.cache;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("cacheEntityService")
@Transactional
public class CacheEntryServiceImpl implements CacheEntryService {
	 
    @Autowired
    private CacheEntryDao cacheEntryDao;
    
	@Override
	public void save(CacheEntry entry) {
		cacheEntryDao.save(entry);
	}

	@Override
	public CacheEntry findCacheEntryByOperands(String operands) {
		return cacheEntryDao.findCacheEntryByOperands(operands);
	}

	@Override
	public void clearCacheEntries() {
		cacheEntryDao.clearCacheEntries();
	}

}
