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

import com.github.yingzhuo.carnival.refuse.RefuseConfig;
import com.github.yingzhuo.carnival.refuse.RefuseConfigLoader;

/**
 * @author 应卓
 * @see RefuseConfigLoader
 */
public class AlawaysEmptyRefuseConfigLoader implements RefuseConfigLoader {

    private static final RefuseConfig EMPTY = new RefuseConfig();

    @Override
    public RefuseConfig load() {
        return EMPTY;
    }

}
