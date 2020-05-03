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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author 应卓
 */
@Slf4j
public class SnowflakeStringIdGenerator implements StringIdGenerator, InitializingBean {

    private final SnowflakeLongIdGenerator delegate;
    private final int length;
    private final char padCharacter;
    private final long workerId;
    private final long dataCenterId;

    public SnowflakeStringIdGenerator(long workerId, long dataCenterId, int length, char padCharacter) {
        this.delegate = new SnowflakeLongIdGenerator(workerId, dataCenterId);
        this.length = length;
        this.padCharacter = padCharacter;
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    @Override
    public String nextId() {
        StringBuilder id = new StringBuilder(String.valueOf(delegate.nextId()));
        while (id.length() < this.length) {
            id.insert(0, padCharacter);
        }
        return id.toString();
    }

    @Override
    public void afterPropertiesSet() {
        log.info("snowflake worker-id: {}", workerId);
        log.info("snowflake data-center-id: {}", dataCenterId);
    }
}
