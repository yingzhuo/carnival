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
import com.github.yingzhuo.snowflake.Snowflake;

import java.util.List;

/**
 * @author 应卓
 * @since 1.8.6
 */
public class SnowflakeClientAdapter implements LongIdGenerator {

    @Override
    public Long nextId() {
        return Snowflake.nextId();
    }

    @Override
    public List<Long> nextId(int n) {
        return Snowflake.nextIds(n);
    }

}
