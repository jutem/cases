package com.jutem.cases.config.rpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.jutem.cases.rpc.core.server.RpcServer;
import com.jutem.cases.rpc.core.server.ServiceRegistry;

@Configuration
@PropertySource("classpath:common.properties")
@ComponentScan("com.jutem.cases.rpc.core.server")
public class RpcServerConfig {
	
	@Autowired
	private Environment env;
	
	// 服务端
	@Bean
	public ServiceRegistry serviceRegistry() {
		String registryAddress = env.getProperty("rpc.registry.address");
		ServiceRegistry bean = new ServiceRegistry(registryAddress);
		return bean;
	}

	@Bean
	public RpcServer rpcServer(ServiceRegistry serviceRegistry) {
		String serverAddress = env.getProperty("rpc.server.address");
		RpcServer bean = new RpcServer(serverAddress, serviceRegistry);
		return bean;
	}
}
