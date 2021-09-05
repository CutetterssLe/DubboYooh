package com.mystery.provider;

import com.mystery.framework.URL;
import com.mystery.framework.protocol.NettyServer;
import com.mystery.framework.register.LocalRegister;
import com.mystery.framework.register.ZookeeperRegister;
import com.mystery.provider.service.HelloService;
import com.mystery.provider.service.impl.HelloServiceImpl;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Mystery
 */
public class ProviderServer {

    public static void main(String[] args) {

        String interfaceName = HelloService.class.getName();
        try {
            URL url = new URL(InetAddress.getLocalHost().getHostAddress(), 8081);
            LocalRegister.regist(interfaceName, HelloServiceImpl.class);
            ZookeeperRegister.regist(interfaceName, url);

            NettyServer nettyServer = new NettyServer();
            nettyServer.start(url.getHostName(), url.getPort());

            System.out.println("成功暴露服務：地址為：" + url.getHostName() + "，端口為：" + url.getPort());
        } catch (UnknownHostException uex) {
            uex.printStackTrace();
        }


    }
}
