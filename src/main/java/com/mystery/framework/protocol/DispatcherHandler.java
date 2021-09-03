package com.mystery.framework.protocol;

import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.*;

/**
 * @author Mystery
 */
public class DispatcherHandler implements ChannelHandler{

    private ChannelHandler channelHandler;

    private ExecutorService executorService;

    private static BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(10);

    public DispatcherHandler(ChannelHandler channelHandler) {
        this.channelHandler = channelHandler;
        executorService = new ThreadPoolExecutor(1, 10, 5, TimeUnit.SECONDS,
                blockingQueue, new ThreadPoolExecutor.AbortPolicy());
    }

    @Override
    public void handler(ChannelHandlerContext context, Invocation invocation) throws Exception {
        System.out.println("线程池执行。。。");
        executorService.execute(new Task(invocation, channelHandler, context));
    }
}
