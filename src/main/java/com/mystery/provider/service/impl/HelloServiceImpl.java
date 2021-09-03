package com.mystery.provider.service.impl;

import com.mystery.provider.service.HelloService;

/**
 * @author Mystery
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "hello" + name;
    }
}
