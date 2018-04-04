package com.jutem.cases.javabase;

import org.junit.Test;

import java.lang.reflect.Proxy;

public class ProxyHandlerTest {
    @Test
    public void returnTypeTest() {

        Proxied obj = () -> System.out.println("Object test");

        Proxied proxied = (Proxied) Proxy.newProxyInstance(Proxied.class.getClassLoader(), new Class<?>[]{Proxied.class}, new ProxyHandler(obj));
        proxied.test();
    }

}
