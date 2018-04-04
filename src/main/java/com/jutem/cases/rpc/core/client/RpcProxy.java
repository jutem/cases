package com.jutem.cases.rpc.core.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jutem.cases.config.CasesConfig;
import com.jutem.cases.rpc.core.common.RpcRequest;
import com.jutem.cases.rpc.core.common.RpcResponse;

public class RpcProxy {
	
	private Logger logger = LoggerFactory.getLogger(CasesConfig.class);
	
	private String serverAddress;
	private ServiceDiscovery serviceDiscovery;
	
	public RpcProxy(String serverAddress) {
		this.serverAddress = serverAddress;
	}
	
	public RpcProxy(ServiceDiscovery serviceDiscovery) {
		this.serviceDiscovery = serviceDiscovery;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T create(Class<?> interfaceClass) {
		return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), 
				new Class<?>[]{interfaceClass}, new InvocationHandler() {
					public Object invoke(Object proxy, Method method, Object[] args)
							throws Throwable {
						RpcRequest request = new RpcRequest();
						request.setRequestId(UUID.randomUUID().toString());
						request.setClassName(method.getDeclaringClass().getName());
						request.setMethodName(method.getName());
						request.setParameterTypes(method.getParameterTypes());
						request.setParameters(args);
						if(serviceDiscovery != null) 
							serverAddress = serviceDiscovery.discover();
						String[] array = serverAddress.split(":");
						String host = array[0];
						
						int port = Integer.parseInt(array[1]);
						
						logger.debug("send address " + serverAddress);
						
						RpcClient client = new RpcClient(host, port);
						logger.debug("client inited");
						RpcResponse response = client.send(request);
						
						if(response.getError() != null) {
							throw response.getError();
						} else {
							return response.getResult();
						}
					}
				});
	}
}
