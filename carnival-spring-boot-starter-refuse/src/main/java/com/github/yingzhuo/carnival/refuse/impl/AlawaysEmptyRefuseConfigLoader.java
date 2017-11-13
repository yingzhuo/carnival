/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.refuse.impl;

import com.github.yingzhuo.carnival.refuse.RefuseConfigLoader;

import java.util.Collections;
import java.util.Map;

/**
 * @author 应卓
 * @see RefuseConfigLoader
 */
public class AlawaysEmptyRefuseConfigLoader implements RefuseConfigLoader {

    private static final Map<String, String> EMPTY = Collections.emptyMap();

    @Override
    public Map<String, String> load() {
        return EMPTY;
    }

}
