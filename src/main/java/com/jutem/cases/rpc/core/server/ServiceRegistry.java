package com.jutem.cases.rpc.core.server;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jutem.cases.config.CasesConfig;
import com.jutem.cases.rpc.core.common.Constant;

public class ServiceRegistry {
	
	private Logger logger = LoggerFactory.getLogger(CasesConfig.class);
	
	private CountDownLatch latch = new CountDownLatch(1);
	private String registryAddress;
	
	public ServiceRegistry(String registryAddress) {
		this.registryAddress = registryAddress;
	}
	
	public void register(String data) {
		if(data != null) {
			ZooKeeper zk = connectServer();
			if(zk != null) {
				createNode(zk, data);
			}
		}
	}
	
	private ZooKeeper connectServer() {
		ZooKeeper zk = null;
		try {
			zk = new ZooKeeper(registryAddress, Constant.ZK_SESSION_TIMEOUT, new Watcher() {
				
				public void process(WatchedEvent event) {
					if(event.getState() == Event.KeeperState.SyncConnected) {
						latch.countDown();
					}
				}
			});
			latch.await();
		} catch(InterruptedException e) {
			logger.error("", e);
		} catch(IOException e) {
			logger.error("", e);
		}
		return zk;
	}
	
	private void createNode(ZooKeeper zk, String data) {
		try {
			byte[] bytes = data.getBytes();
			String path = zk.create(Constant.ZK_DATA_PATH, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			logger.debug("path : " + path + " | data : " + data);
		} catch(KeeperException e) {
			logger.error("", e);
		} catch(InterruptedException e) {
			logger.error("", e);
		}
	}
	
}
