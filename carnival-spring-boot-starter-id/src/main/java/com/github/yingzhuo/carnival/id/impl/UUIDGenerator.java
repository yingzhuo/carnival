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

import java.util.UUID;

/**
 * @author 应卓
 * @since 1.6.19
 */
public class UUIDGenerator implements StringIdGenerator {

    private final boolean shortVersion;

    public UUIDGenerator(boolean shortVersion) {
        this.shortVersion = shortVersion;
    }

    @Override
    public String nextId() {
        if (shortVersion) {
            return UUID.randomUUID().toString().replaceAll("-", "");
        } else {
            return UUID.randomUUID().toString();
        }
    }

}
