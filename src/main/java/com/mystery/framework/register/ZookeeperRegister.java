package com.mystery.framework.register;

import com.alibaba.fastjson.JSONObject;
import com.mystery.framework.URL;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mystery
 */
public class ZookeeperRegister {

    private static final CuratorFramework CLIENT;

    private static final Map<String, List<URL>> URL_CACHE = new ConcurrentHashMap<>();

    static {
        CLIENT = CuratorFrameworkFactory
                .newClient("172.16.0.10:2181",
                        new RetryNTimes(3, 1000));
        CLIENT.start();
    }

    private static final Map<String, List<URL>> REGISTER = new ConcurrentHashMap<>();

    public static void regist(String interfaceName, URL url) {
        try {
            String result = CLIENT.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(String.format("/dubbo/service/%s/%s", interfaceName,
                            JSONObject.toJSONString(url)), null);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<URL> get(String interfaceName) {
        if (URL_CACHE.containsKey(interfaceName)) {
            return URL_CACHE.get(interfaceName);
        }
        List<URL> urlList = new ArrayList<>();
        try {
            List<String> result = CLIENT.getChildren().forPath(String.format("/dubbo/service/%s", interfaceName));
            for (String url : result) {
                urlList.add(JSONObject.parseObject(url, URL.class));
            }
            URL_CACHE.put(interfaceName, urlList);
            return urlList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
