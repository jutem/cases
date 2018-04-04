package com.jutem.cases.spring.cache;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jutem.cases.base.BaseTest;

public class SpringCacheTest extends BaseTest{
	
	@Autowired
	private SpringCache springCache;
	
	private static final String SET_STRING = "1";
	private static final String UPDATE_STRING = "2";
	
	@Test
	public void cacheOnlyTest() {
		CacheModel cacheModel = springCache.getCahceOnly(1L);
		Assert.assertNull(cacheModel);
		cacheModel = springCache.getCahceOnly(1L);
		Assert.assertNull(cacheModel);
		int passCacheNum = springCache.getPassCacheNum();
		Assert.assertEquals(passCacheNum, 1);
	}
	
	@Test
	public void commonCacheTest() {
		CacheModel cacheModel = new CacheModel();
		cacheModel.setMessage(SET_STRING);
		
		CacheModel s1 = springCache.setModel(cacheModel);
		Assert.assertEquals(s1.getId().longValue(), 1L);
		int passCacheNum = springCache.getPassCacheNum();
		Assert.assertEquals(passCacheNum, 1);
		
		CacheModel g1 = springCache.getModel(1L);
		Assert.assertEquals(g1.getId().longValue(), 1L);
		passCacheNum = springCache.getPassCacheNum();
		Assert.assertEquals(passCacheNum, 1);
		
		s1.setMessage(UPDATE_STRING);
		CacheModel u1 = springCache.updateModel(s1);
		Assert.assertEquals(u1.getId().longValue(), 1L);
		passCacheNum = springCache.getPassCacheNum();
		Assert.assertEquals(passCacheNum, 2);
		
		CacheModel g2 = springCache.getModel(1L);
		Assert.assertEquals(g2.getMessage(), UPDATE_STRING);
		
		springCache.deleteModel(g2.getId());
		passCacheNum = springCache.getPassCacheNum();
		Assert.assertEquals(passCacheNum, 3);

		CacheModel g3 = springCache.getModel(1L);
		Assert.assertNull(g3);
	}
	
}
