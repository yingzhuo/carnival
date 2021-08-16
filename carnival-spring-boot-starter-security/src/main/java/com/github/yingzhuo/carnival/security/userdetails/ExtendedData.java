/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.userdetails;

import java.util.HashMap;

/**
 * @author 应卓
 * @since 1.10.7
 */
public final class ExtendedData extends HashMap<String, Object> {

    private ExtendedData() {
    }

    public static ExtendedData newInstance() {
        return new ExtendedData();
    }

    public ExtendedData add(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
