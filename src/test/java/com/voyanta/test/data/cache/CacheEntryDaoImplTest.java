package com.voyanta.test.data.cache;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CacheEntryDaoImplTest {
	@Mock
	SessionFactory sessionFactory;
	
	@Mock
	Session session;
	
	@Mock
	CacheEntryDaoImpl cacheEntryDaoImpl;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		cacheEntryDaoImpl.setSessionFactory(sessionFactory);
	}
	
	@Test
	@Ignore
	public void save_entityPassed_entityIsSavedIsSession(){
		//	given
		CacheEntry entry = new CacheEntry();
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		
		//	when
		cacheEntryDaoImpl.save(entry);
		
		//	then
		verify(session).persist(entry);
	}
	
	@Test
	@Ignore
	public void findCacheEntryByOperands_operandsPassed_operandsAreUsedToSearch(){
		//	given
		String operands = "operands";
		Criteria criteria = mock(Criteria.class);
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		when(session.createCriteria((Class<?>) any())).thenReturn(criteria);
		
		//	when
		cacheEntryDaoImpl.findCacheEntryByOperands(operands);
		
		//	then
		verify(criteria).add((Criterion) any());
	}
	
	@Test
	@Ignore
	public void findCacheEntryByOperands_called_uniqueValuewIsCalled(){
		//	given
		String operands = "operands";
		CacheEntry entry = new CacheEntry();
		Criteria criteria = mock(Criteria.class);
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		when(session.createCriteria((Class<?>) any())).thenReturn(criteria);
		when(criteria.uniqueResult()).thenReturn(entry);
		
		//	when
		CacheEntry actualEntry = cacheEntryDaoImpl.findCacheEntryByOperands(operands);
		
		//	then
		assertEquals(entry, actualEntry);
	}
}
