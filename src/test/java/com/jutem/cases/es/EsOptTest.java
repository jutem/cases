package com.jutem.cases.es;

import com.alibaba.fastjson.JSON;
import com.jutem.cases.base.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Slf4j
public class EsOptTest extends BaseTest {


    @Autowired
    private EsOpt esOpt;

    @Test
    public void templateSearchTest() {
        Date today = new Date();
        Date yesterday = new Date(today.getTime() - 3600 * 24);
        String sr = esOpt.templateSearch(yesterday, today);
        log.info(sr);
    }

    @Test
    public void putTemplate() {
        String result = esOpt.putTemplate("template_gender_without_mustache",
                "{\"size\":10,\"query\":{\"bool\":{\"filter\":[{\"term\":{\"status\":70}}]}}}");
        log.info(JSON.toJSONString(result));
    }

    @Test
    public void getTemplate() {
        String result = esOpt.getTemplate("template_gender_without_mustache");
        System.out.println((JSON.toJSONString(result)));
    }


}
