package com.voyanta.test.data.cache;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.voyanta.test.data.AbstractDao;

@Repository("cacheEntry")
public class CacheEntryDaoImpl extends AbstractDao<String, CacheEntry> implements CacheEntryDao {

	@Override
	public void save(CacheEntry entry) {
		persist(entry);
	}

	@Override
	public CacheEntry findCacheEntryByOperands(String operands) {
        Criteria criteria = createEntityCriteria();
        
        criteria.add(Restrictions.like("operands", operands));
        
        return (CacheEntry) criteria.uniqueResult();
	}

	@Override
	public void clearCacheEntries() {
    	Query query = getSession()
    			.createSQLQuery("DELETE FROM addition_cache")
    			.addEntity(CacheEntry.class);
    	
    	query.executeUpdate();
	}
}
