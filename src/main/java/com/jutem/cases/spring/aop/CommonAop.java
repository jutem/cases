package com.jutem.cases.spring.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jutem.cases.config.CasesConfig;

/**
 * 环绕注解的aop
 */
@Component
@Aspect
public class CommonAop {
	
	private Logger logger = LoggerFactory.getLogger(CasesConfig.class);
	
	@Pointcut("execution(** com.jutem.cases.spring.aop.AroundSample.*(..)) && " +
	"@annotation(com.jutem.cases.spring.aop.CommonAround)")
	public void aroundAnnotation(){}

	@Around("aroundAnnotation()")
	public Object around(ProceedingJoinPoint jp) {
		try {
			MethodSignature signature = (MethodSignature)jp.getSignature();
			Method method = signature.getMethod();
			CommonAround commonAround = method.getAnnotation(CommonAround.class);
			logger.info("<<<<<<< annotation value : " + commonAround.value());
			logger.info("<<<<<<< around method name: " + method.getName());
			return jp.proceed(jp.getArgs());
		} catch(Throwable e) {
			logger.error(e.getMessage());
			return null;
		} 
	}
}
