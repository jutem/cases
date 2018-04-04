package com.jutem.cases.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.jutem.cases.config.rpc.RpcClientConfig;
import com.jutem.cases.config.rpc.RpcServerConfig;
import com.jutem.cases.traffic.guava.RateLimiterShaper;

/**
 * @author chendawei 
 * spring配置类
 */
@Configuration
@EnableAspectJAutoProxy
@EnableCaching
@PropertySource("classpath:common.properties")
@ComponentScan(value = {"com.jutem.cases"}, excludeFilters={
		  @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, value=RpcServerConfig.class),
		  @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, value=RpcClientConfig.class)
}) 
public class CasesConfig {
	
	private Logger logger = LoggerFactory.getLogger(CasesConfig.class);
	
	@Autowired
	private Environment env;
	
	/******** 令牌桶相关 ********/
	@Bean
	public RateLimiterShaper rateLimiterShaper() {
		Double qps = env.getProperty("traffic.guava", Double.class);
		return new RateLimiterShaper(qps);
	}
	
	/******** spring缓存支持 ********/
	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager();
	}
}
