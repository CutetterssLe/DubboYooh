package com.mystery.framework.protocol;

import com.mystery.framework.URL;
import com.mystery.framework.register.LocalRegister;
import com.mystery.framework.register.ZookeeperRegister;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Mystery
 */
public class RequestHandler implements ChannelHandler{


    @Override
    public void handler(ChannelHandlerContext context, Invocation invocation) throws Exception {
        Class<?> clazz = LocalRegister.get(invocation.getInterfaceName());

        Method method = clazz.getMethod(invocation.getInterfaceName(), invocation.getParamTypes());

        Object result = method.invoke(clazz.newInstance(), invocation.getParams());

        context.writeAndFlush("Netty：" + result);
    }
}
