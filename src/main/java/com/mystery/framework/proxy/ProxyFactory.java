package com.mystery.framework.proxy;

import com.mystery.framework.LoadBalance;
import com.mystery.framework.URL;
import com.mystery.framework.protocol.Invocation;
import com.mystery.framework.protocol.NettyClient;
import com.mystery.framework.register.ZookeeperRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author Mystery
 */
public class ProxyFactory<T> {

    @SuppressWarnings("unchecked")
    public static <T> T getProxy(final Class interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Invocation invocation = new Invocation(interfaceClass.getName(), method.getName(), method.getParameterTypes(), args);
                try {
                    NettyClient client = new NettyClient();
                    List<URL> urlList = ZookeeperRegister.get(interfaceClass.getName());
                    URL url = LoadBalance.random(urlList);
                    System.out.println("调用方地址：" + url);
                    String res = client.send(url.getHostName(), url.getPort(), invocation);
                    return res;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return doMock();
                }
            }
        });
    }

    public static Object doMock() {
        return "容错逻辑";
    }
}
