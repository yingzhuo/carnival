/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.tool.tokendao;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 * @since 1.2.5
 */
public class MapStringTokenDao implements StringTokenDao {

    private final Map<String, String> map = new HashMap<>();

    @Override
    public void save(String key, String token) {
        map.put(key, token);
    }

    @Override
    public String find(String key) {
        return map.get(key);
    }

}
