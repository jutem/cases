package com.jutem.cases.rpc.core.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.jutem.cases.rpc.core.annotation.RpcService;
import com.jutem.cases.rpc.core.coder.RpcDecoder;
import com.jutem.cases.rpc.core.coder.RpcEncoder;
import com.jutem.cases.rpc.core.coder.RpcHandler;
import com.jutem.cases.rpc.core.common.RpcRequest;
import com.jutem.cases.rpc.core.common.RpcResponse;

public class RpcServer implements ApplicationContextAware, InitializingBean{

	private Logger logger = LoggerFactory.getLogger(RpcServer.class);
	
	private String serverAddress;
	private ServiceRegistry serviceRegistry;
	
	private Map<String, Object> handlerMap = new HashMap<String, Object>();
	
	public RpcServer(String serverAddress) {
		this.serverAddress = serverAddress;
	}
	
	public RpcServer(String serverAddress, ServiceRegistry serviceRegistry) {
		this.serverAddress = serverAddress;
		this.serviceRegistry = serviceRegistry;
	}
	
	public void afterPropertiesSet() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap  bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<Channel>() {
					@Override
					protected void initChannel(Channel channel) throws Exception {
						channel.pipeline()
							.addLast(new RpcDecoder(RpcRequest.class))
							.addLast(new RpcEncoder(RpcResponse.class))
							.addLast(new RpcHandler(handlerMap));
					}
				})
				.option(ChannelOption.SO_BACKLOG, 128)
				.childOption(ChannelOption.SO_KEEPALIVE, true);
			String[] array = serverAddress.split(":");
			String host = array[0];
			int port = Integer.parseInt(array[1]);
			
			ChannelFuture future = bootstrap.bind(host, port).sync();
			logger.debug("server started on address " + serverAddress);
			
			if(serviceRegistry != null)
				serviceRegistry.register(serverAddress);
			
			future.channel().closeFuture().sync();

		} finally {
			workGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(RpcService.class);
		if(MapUtils.isNotEmpty(serviceBeanMap)) {
			for(Object serviceBean : serviceBeanMap.values()) {
				String interfaceName = serviceBean.getClass().getAnnotation(RpcService.class).value().getName();
				handlerMap.put(interfaceName, serviceBean);
			}
		}
	}

}
