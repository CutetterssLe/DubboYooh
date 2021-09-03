package com.mystery.framework;

import java.util.List;
import java.util.Random;

/**
 * @author Mystery
 */
public class LoadBalance {

    public static URL random(List<URL> urlList) {
        Random r = new Random();
        int i = r.nextInt(urlList.size());
        return urlList.get(i);
    }
}
