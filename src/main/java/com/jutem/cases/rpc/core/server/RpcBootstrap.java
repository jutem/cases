package com.jutem.cases.rpc.core.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.jutem.cases.config.rpc.RpcServerConfig;

public class RpcBootstrap {

    private static final Logger logger = LoggerFactory.getLogger(RpcBootstrap.class);

    @SuppressWarnings("resource")
	public static void main(String[] args) {
        logger.debug("start server");
        new AnnotationConfigApplicationContext(RpcServerConfig.class);
    }
}
