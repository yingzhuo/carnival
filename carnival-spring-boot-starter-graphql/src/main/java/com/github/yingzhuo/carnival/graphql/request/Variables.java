/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.graphql.request;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 * @since 1.10.14
 */
public final class Variables extends HashMap<String, Object> implements Map<String, Object> {

    public static Variables fromMap(Map<String, Object> map) {
        Variables variables = new Variables();
        if (map != null && !map.isEmpty()) {
            variables.putAll(map);
        }
        return variables;
    }

}
