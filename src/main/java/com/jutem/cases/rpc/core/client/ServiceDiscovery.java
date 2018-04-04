package com.jutem.cases.rpc.core.client;

import io.netty.util.internal.ThreadLocalRandom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jutem.cases.config.CasesConfig;
import com.jutem.cases.rpc.core.common.Constant;

public class ServiceDiscovery {
	private Logger logger = LoggerFactory.getLogger(CasesConfig.class);
	
	private CountDownLatch latch = new CountDownLatch(1);
	
	private volatile List<String> dataList = new ArrayList<String>();
	
	private String registyrAddress;
	
	public ServiceDiscovery(String registryAddress) {
		this.registyrAddress = registryAddress;
		
		ZooKeeper zk = connectServer();
		if(zk != null) {
			watchNode(zk);
		}
	}
	
	public String discover() {
		String data = null;
		int size = dataList.size();
		
		if(size > 0) {
			if(size == 1) {
				data = dataList.get(0);
				logger.info("using only data : " + data);
			} else {
				data = dataList.get(ThreadLocalRandom.current().nextInt(size));
				logger.info("using random data : " + data);
			}
		}
		return data;
	}
	
	private ZooKeeper connectServer() {
		ZooKeeper zk = null;
		try {
			zk = new ZooKeeper(registyrAddress, Constant.ZK_SESSION_TIMEOUT, new Watcher() {
				
				public void process(WatchedEvent event) {
					if(event.getState() == Event.KeeperState.SyncConnected) {
						latch.countDown();
					}
				}
			});
			latch.await();
		} catch(IOException e) {
			logger.error("", e);
		} catch(InterruptedException e) {
			logger.error("", e);
		}
		return zk;
	}
	
	private void watchNode(final ZooKeeper zk) {
		try {
			List<String> nodeList = zk.getChildren(Constant.ZK_REGISTRY_PATH, new Watcher() {
				
				public void process(WatchedEvent event) {
					if(event.getType() == Event.EventType.NodeChildrenChanged) {
						watchNode(zk);
					}
				}
			});
			List<String> dataList = new ArrayList<String>();
			for(String node : nodeList) {
				byte[] bytes = zk.getData(Constant.ZK_REGISTRY_PATH + "/" + node, false, null);
				dataList.add(new String(bytes));
			}
			this.dataList = dataList;
		} catch(KeeperException e) {
			logger.error("", e);
		} catch(InterruptedException e) {
			logger.error("", e);
		}
	}
	
	
}
