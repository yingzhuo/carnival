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

import com.github.yingzhuo.carnival.refuse.RefusingConfig;
import com.github.yingzhuo.carnival.refuse.RefusingConfigLoader;

/**
 * @author 应卓
 * @see RefusingConfigLoader
 */
public class AlawaysEmptyRefusingConfigLoader implements RefusingConfigLoader {

    private static final RefusingConfig EMPTY = new RefusingConfig();

    @Override
    public RefusingConfig load() {
        return EMPTY;
    }

}
