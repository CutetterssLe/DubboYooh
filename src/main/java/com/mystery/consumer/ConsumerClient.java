package com.mystery.consumer;

import com.mystery.framework.proxy.ProxyFactory;
import com.mystery.provider.service.HelloService;

/**
 * @author Mystery
 */
public class ConsumerClient {
    public static void main(String[] args) {
        HelloService proxy = (HelloService) ProxyFactory.getProxy(HelloService.class);
//        System.out.println(proxy);
        for (int i = 0; i < 10; i++) {
            System.out.println(proxy.sayHello("你好"));
        }
    }
}
