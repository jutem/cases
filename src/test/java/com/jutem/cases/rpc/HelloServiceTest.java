package com.jutem.cases.rpc;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jutem.cases.config.CasesConfig;
import com.jutem.cases.config.rpc.RpcClientConfig;
import com.jutem.cases.rpc.core.client.HelloService;
import com.jutem.cases.rpc.core.client.RpcProxy;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RpcClientConfig.class)
public class HelloServiceTest {

	private Logger logger = LoggerFactory.getLogger(CasesConfig.class);

	@Autowired
	private RpcProxy rpcProxy;

	@Test
	public void helloTest() {
		HelloService helloService = rpcProxy.create(HelloService.class);
		String result = helloService.hello("World");
		Assert.assertEquals("Hello! World", result);
	}
}
