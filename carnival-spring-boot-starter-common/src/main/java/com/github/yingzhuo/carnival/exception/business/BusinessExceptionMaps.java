/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.exception.business;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 * @since 1.6.14
 */
public interface BusinessExceptionMaps {

    public static Map<String, String> merge(Map1 map1, Map2 map2, Map3 map3) {
        final Map<String, String> map = new HashMap<>();

        if (map1 != null && !map1.isEmpty()) {
            map.putAll(map1);
        }

        if (map2 != null && !map2.isEmpty()) {
            map.putAll(map2);
        }

        if (map3 != null && !map3.isEmpty()) {
            map.putAll(map3);
        }

        return map;
    }

    @ConfigurationProperties(prefix = "business.exception")
    public static class Map1 extends HashMap<String, String> {
    }

    @ConfigurationProperties(prefix = "business-exception")
    public static class Map2 extends HashMap<String, String> {
    }

    @ConfigurationProperties(prefix = "be")
    public static class Map3 extends HashMap<String, String> {
    }

}
