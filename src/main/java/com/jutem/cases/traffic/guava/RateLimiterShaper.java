package com.jutem.cases.traffic.guava;

import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.RateLimiter;
import com.jutem.cases.traffic.TrafficShaper;

/**
 * 使用guava中的RateLimiter实现的令牌通
 */
public class RateLimiterShaper implements TrafficShaper{
	
	private final RateLimiter rateLimiter;
	
	public RateLimiterShaper(double qps) {
		super();
		rateLimiter = RateLimiter.create(qps);
	}
	
	public void acquire() {
		rateLimiter.acquire();
	}

	public boolean tryAcquire(Long timeout, TimeUnit unit) {
		return rateLimiter.tryAcquire(timeout, unit);
	}

	public boolean tryAcquire() {
		return rateLimiter.tryAcquire();
	}

	public void acquire(int n) {
		rateLimiter.acquire(n);
	}


}
