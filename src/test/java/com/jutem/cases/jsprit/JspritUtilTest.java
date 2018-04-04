package com.jutem.cases.jsprit;

import com.jutem.cases.base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class JspritUtilTest extends BaseTest{

    @Autowired
    private JspritUtil jspritUtil;

    @Test
    public void Demon() {
        jspritUtil.Demon();
    }

    @Test
    public void multiTest() {
        jspritUtil.MultiVehicleAndJob(10, 10, 20);
    }
}
