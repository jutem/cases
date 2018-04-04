package com.jutem.cases.spring.aop;

import org.springframework.stereotype.Component;

@Component
public class AroundSample {
	
	@CommonAround(10)
	public void aroundMethod() {
	}

	public void notAroundMethod() {
	}
}
