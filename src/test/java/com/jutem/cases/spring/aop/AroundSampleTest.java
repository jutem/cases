package com.jutem.cases.spring.aop;

import com.jutem.cases.base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AroundSampleTest extends BaseTest{
	
	@Autowired
	private AroundSample aroundSample;
	
	@Test
	public void around() {
		aroundSample.aroundMethod();
		aroundSample.notAroundMethod();
	}
}
