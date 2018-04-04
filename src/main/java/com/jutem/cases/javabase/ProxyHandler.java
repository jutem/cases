package com.jutem.cases.javabase;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler implements InvocationHandler {

    private Proxied proxied;

    public ProxyHandler(Proxied proxied) {
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("hello test");
        return method.invoke(proxied, args);
    }
}
