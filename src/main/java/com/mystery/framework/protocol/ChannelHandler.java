package com.mystery.framework.protocol;

import io.netty.channel.ChannelHandlerContext;

public interface ChannelHandler {

    void handler(ChannelHandlerContext context, Invocation invocation) throws Exception;
}
