package com.mystery.framework.protocol;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author Mystery
 */
public class Task implements Runnable{

    private Invocation invocation;

    private ChannelHandler channelHandler;

    private ChannelHandlerContext context;

    public Task(Invocation invocation, ChannelHandler channelHandler, ChannelHandlerContext context) {
        this.invocation = invocation;
        this.channelHandler = channelHandler;
        this.context = context;
    }

    @Override
    public void run() {
        try {
            channelHandler.handler(context, invocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
