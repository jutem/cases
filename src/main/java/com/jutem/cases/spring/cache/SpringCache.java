package com.jutem.cases.spring.cache;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class SpringCache {
	
	private Logger logger = LoggerFactory.getLogger(SpringCache.class);
	
	private Map<Long, CacheModel> persistMap = new HashMap<Long, CacheModel>();
	private int passCacheNum = 0;
	
	@CachePut(value = "springCache", key="#result.id")
	public CacheModel setModel(CacheModel model) {
		passCacheNum ++;
		Long id = persistMap.size() + 1L;
		model.setId(id);
		persistMap.put(model.getId(), model);
		return model;
	};
	
	@CachePut(value = "springCache", key="#result.id")
	public CacheModel updateModel(CacheModel model) {
		passCacheNum ++;
		persistMap.put(model.getId(), model);
		return model;
	}
	
	@Cacheable("springCache")
	public CacheModel getModel(long id) {
		passCacheNum ++;
		return persistMap.get(id);
	}
	
	@CacheEvict("springCache")
	public void deleteModel(long id) {
		passCacheNum ++;
		persistMap.remove(id);
	}
	
	@Cacheable("springCache")
	public CacheModel getCahceOnly(long id) {
		passCacheNum ++;
		return null;
	}

	public int getPassCacheNum() {
		return passCacheNum;
	}
	
}
