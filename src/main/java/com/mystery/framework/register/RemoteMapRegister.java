package com.mystery.framework.register;

import com.mystery.framework.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mystery
 */
public class RemoteMapRegister {

    private static final Map<String, List<URL>> REGISTER = new ConcurrentHashMap<>();


    public static void regist(String interfaceName, URL url) {
        List<URL> urls = REGISTER.get(interfaceName);
        if (urls == null) {
            urls = new ArrayList<>();
        }
        urls.add(url);

        REGISTER.put(interfaceName, urls);
    }

    public static List<URL> get(String interfaceName) {
        return REGISTER.get(interfaceName);
    }
}
