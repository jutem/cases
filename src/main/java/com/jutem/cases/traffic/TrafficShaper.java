package com.jutem.cases.traffic;

import java.util.concurrent.TimeUnit;

public interface TrafficShaper {
	public void acquire();
	public void acquire(int n);
	public boolean tryAcquire();
	public boolean tryAcquire(Long timeout, TimeUnit unit);
}
