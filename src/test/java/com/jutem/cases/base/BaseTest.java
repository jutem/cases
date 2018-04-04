package com.jutem.cases.base;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jutem.cases.config.CasesConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=CasesConfig.class)
public abstract class BaseTest {
	protected Logger logger = LoggerFactory.getLogger(BaseTest.class);
}
