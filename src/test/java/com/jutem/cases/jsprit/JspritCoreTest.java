package com.jutem.cases.jsprit;

import com.jutem.cases.base.BaseTest;
import org.junit.Test;

public class JspritCoreTest extends BaseTest{
    @Test
    public void bootstrap() {
        VrpCore vrpCore = new VrpCore();
        vrpCore.bootstrap();
    }

}
