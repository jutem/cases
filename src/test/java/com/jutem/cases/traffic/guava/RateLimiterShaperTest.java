package com.jutem.cases.traffic.guava;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.jutem.cases.base.BaseTest;
import com.jutem.cases.config.CasesConfig;
import com.jutem.cases.traffic.TrafficShaper;

public class RateLimiterShaperTest extends BaseTest{
	
	private Logger logger = LoggerFactory.getLogger(CasesConfig.class);
	
	@Autowired
	@Qualifier("rateLimiterShaper")
	private TrafficShaper trafficShaper;
	
	private static AtomicInteger count = new AtomicInteger(0);

	/** 控制任务提交速度*/
	@Test
	public void submitTasks() {
		
		//生产100个任务
		List<Runnable> tasks = new LinkedList<Runnable>();
		for(int i = 0; i < 100; i++) {
			Runnable task = new Runnable() {
				public void run() {
					logger.info("cout : " + count.incrementAndGet());
				}
			};
			tasks.add(task);
		}
		
		//设置线程池
		Executor executor = Executors.newFixedThreadPool(10);
		
	    for (Runnable task : tasks) {
	        trafficShaper.acquire(10);
	        executor.execute(task);
	    }
	}
	
	/** 控制数据流平均每秒数据流大小 */
	@Test
	public void submitPacket() {
		byte[] packet = new byte[10];
		trafficShaper.acquire(packet.length);
		//networkservice.send(packet)
	}
}
