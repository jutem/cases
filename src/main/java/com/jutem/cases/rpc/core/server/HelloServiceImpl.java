package com.jutem.cases.rpc.core.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jutem.cases.rpc.core.annotation.RpcService;
import com.jutem.cases.rpc.core.client.HelloService;

@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {

	private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);
	
	public String hello(String name) {
		logger.debug("<<<< name : " + name);
		return "Hello! " + name;
	}
	
}
