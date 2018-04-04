package com.jutem.cases.config.rpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.jutem.cases.rpc.core.client.RpcProxy;
import com.jutem.cases.rpc.core.client.ServiceDiscovery;

@Configuration
@PropertySource("classpath:common.properties")
public class RpcClientConfig {
	
	@Autowired
	private Environment env;
	
	// 客户端
	@Bean
	public ServiceDiscovery serviceDiscovery() {
		String registryAddress = env.getProperty("rpc.registry.address");
		ServiceDiscovery bean = new ServiceDiscovery(registryAddress);
		return bean;
	}

	@Bean
	public RpcProxy rpcProxy(ServiceDiscovery serviceDiscovery) {
		RpcProxy bean = new RpcProxy(serviceDiscovery);
		return bean;
	}
	
}
