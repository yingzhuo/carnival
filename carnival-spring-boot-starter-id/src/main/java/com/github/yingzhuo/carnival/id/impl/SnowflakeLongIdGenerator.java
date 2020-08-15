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

import com.github.yingzhuo.carnival.id.LongIdGenerator;
import com.github.yingzhuo.codegen4j.SnowflakeGenerator;

/**
 * @author 应卓
 */
public class SnowflakeLongIdGenerator implements LongIdGenerator {

    private final SnowflakeGenerator generator;

    public SnowflakeLongIdGenerator(long workerId, long dataCenterId) {
        this.generator = new SnowflakeGenerator(workerId, dataCenterId);
    }

    @Override
    public Long nextId() {
        return generator.next();
    }

}
