package com.jutem.cases.spring.bootstrap;

import com.jutem.cases.config.CasesConfig;
import com.jutem.cases.traffic.guava.RateLimiterShaper;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Bootstrap {
    @Test
    public void bootstrap() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(CasesConfig.class);
        RateLimiterShaper shaper = ctx.getBean(RateLimiterShaper.class);
        if(shaper != null)
            System.out.println("done");
    }

;

}
