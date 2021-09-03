package com.mystery.framework.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.concurrent.*;

/**
 * @author Mystery
 */
public class NettyClient {

    public NettyClientHandler handler = null;

    private static BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(10);

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 10, 5, TimeUnit.SECONDS,
            blockingQueue, new ThreadPoolExecutor.AbortPolicy());

    public void start(String hostName, Integer port) {
        handler = new NettyClientHandler();
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
                        pipeline.addLast("encoder", new ObjectEncoder());
                        pipeline.addLast("handler", handler);
                    }
                });
        try {
            bootstrap.connect(hostName, port).sync();
        } catch (InterruptedException ite) {
            ite.printStackTrace();
        }
    }

    public String send(String hostName, Integer port, Invocation invocation) {
        if (handler == null) {
            start(hostName, port);
        }
        handler.setInvocation(invocation);
        try {
            return executor.submit(handler).get().toString();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
