/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.redis.autoconfig;

import com.github.yingzhuo.carnival.redis.lock.RedisLockBean;
import com.github.yingzhuo.carnival.redis.lock.RedisLockBeanImpl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author 应卓
 * @since 1.6.9
 */
@EnableConfigurationProperties(RedisLockAutoConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.redis.lock", name = "enabled", havingValue = "true", matchIfMissing = true)
public class RedisLockAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public RedisLockBean redisLockBean(RedisConnectionFactory factory, Props props) {
        final RedisLockBeanImpl bean = new RedisLockBeanImpl(factory);
        bean.setDefaultMax(props.getDefaultTimeToLive());
        bean.setPrefix(props.getKeyPrefix());
        bean.setSuffix(props.getKeySuffix());
        return bean;
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.redis.lock")
    static class Props {
        private boolean enabled = true;
        private String keyPrefix = "carnival-redis-lock-";
        private String keySuffix = "";
        @DurationUnit(ChronoUnit.SECONDS)
        private Duration defaultTimeToLive = Duration.ofSeconds(10L);
    }

}
