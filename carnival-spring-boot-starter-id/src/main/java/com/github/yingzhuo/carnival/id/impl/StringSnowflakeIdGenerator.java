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
 */
public class StringSnowflakeIdGenerator implements StringIdGenerator {

    private final LongSnowflakeIdGenerator innerGenerator;
    private final int pad;

    public StringSnowflakeIdGenerator(long workerId) {
        this(workerId, 32);
    }

    public StringSnowflakeIdGenerator(long workerId, int pad) {
        this.pad = pad;
        this.innerGenerator = new LongSnowflakeIdGenerator(workerId);
    }

    @Override
    public String nextId() {
        final long nextId = innerGenerator.nextId();

        if (pad <= 0) {
            return Long.toString(nextId);
        } else {
            return String.format("%0" + this.pad + "d", nextId);
        }
    }

    public int getPad() {
        return pad;
    }

}
