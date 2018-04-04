package com.jutem.cases.rpc.core.coder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jutem.cases.config.CasesConfig;
import com.jutem.cases.rpc.core.common.RpcRequest;
import com.jutem.cases.rpc.core.common.RpcResponse;

public class RpcHandler extends SimpleChannelInboundHandler<RpcRequest>{
	
	private Logger logger = LoggerFactory.getLogger(CasesConfig.class);
	
	private final Map<String, Object> handlerMap;
	
	public RpcHandler(Map<String, Object> handlerMap) {
		this.handlerMap = handlerMap;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request)
			throws Exception {
		RpcResponse response = new RpcResponse();
		response.setRequestId(request.getRequestId());
		try {
			Object result = handle(request);
			response.setResult(result);
		} catch(Throwable t) {
			response.setError(t);
		}
		ctx.writeAndFlush(response);
	}
	
	private Object handle(RpcRequest request) throws Throwable{
		String className = request.getClassName();
		Object serviceBean = handlerMap.get(className);
		
		Class<?> serviceClass = serviceBean.getClass();
		String methodName = request.getMethodName();
		Class<?>[] parameterTypes = request.getParameterTypes();
		Object[] parameters = request.getParameters();
		
		FastClass serviceFastClass = FastClass.create(serviceClass);
		FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
		return serviceFastMethod.invoke(serviceBean, parameters);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.error("server caught exception", cause);
		ctx.close();
	}
	
}
