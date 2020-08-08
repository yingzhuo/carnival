/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.id.impl;

import com.github.yingzhuo.carnival.id.StringIdGenerator;

/**
 * @author 应卓
 * @since 1.7.0
 */
public class TimestampStringIdGenerator implements StringIdGenerator {

    @Override
    public synchronized String nextId() {
        return String.valueOf(System.currentTimeMillis());
    }

}
