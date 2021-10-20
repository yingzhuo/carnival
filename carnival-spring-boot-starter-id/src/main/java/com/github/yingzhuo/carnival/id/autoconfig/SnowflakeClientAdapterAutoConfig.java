/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.id.autoconfig;

import com.github.yingzhuo.carnival.id.IdGenerator;
import com.github.yingzhuo.carnival.id.impl.SnowflakeClientAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @since 1.8.6
 */
@ConditionalOnProperty(prefix = "carnival.id", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnClass(name = {"com.github.yingzhuo.snowflake.Snowflake"})
@ConditionalOnMissingBean(IdGenerator.class)
class SnowflakeClientAdapterAutoConfig {

    @Bean
    public IdGenerator<?> idGenerator() {
        return new SnowflakeClientAdapter();
    }

}
