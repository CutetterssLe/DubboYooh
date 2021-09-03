package com.mystery.framework.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mystery
 */
public class LocalRegister {

    private static final Map<String, Class<?>> MAP = new ConcurrentHashMap<>();

    public static void regist(String interfaceName, Class<?> clazz) {
        MAP.put(interfaceName, clazz);
    }

    public static Class<?> get(String interfaceName) {
        return MAP.getOrDefault(interfaceName, null);
    }
}
