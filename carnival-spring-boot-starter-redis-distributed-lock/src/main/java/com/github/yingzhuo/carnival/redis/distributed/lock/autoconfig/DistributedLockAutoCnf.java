/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.redis.distributed.lock.autoconfig;

import com.github.yingzhuo.carnival.redis.distributed.lock.request.RequestIdFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author 应卓
 */
@Slf4j
@EnableConfigurationProperties(DistributedLockAutoCnf.DistributedLockProps.class)
@ConditionalOnProperty(prefix = "carnival.redis-distributed-lock", name = "enabled", havingValue = "true", matchIfMissing = true)
public class DistributedLockAutoCnf {

    @Bean
    @ConditionalOnMissingBean
    public JedisPool jedisPool(DistributedLockProps props) {
        // config 配置暂且只用默认值
        JedisPoolConfig config = new JedisPoolConfig();

        if (props.getMode() == Mode.SINGLE) {
            return new JedisPool(
                    config,
                    props.getJedis().getHost(),
                    props.getJedis().getPort(),
                    props.getJedis().getTimeout(),
                    props.getJedis().getPassword());
        } else {
            return null;
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public JedisSentinelPool jedisSentinelPool(DistributedLockProps props) {

        if (props.getMode() == Mode.SENTINEL) {
            return new JedisSentinelPool(
                    props.getJedis().getMasterName(),
                    Arrays.stream(props.getJedis().getSentinels()).collect(Collectors.toSet()),
                    props.getJedis().getPassword()
            );
        } else {
            return null;
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestIdFactory requestIdFactory() {
        return RequestIdFactory.DEFAULT;
    }

    // ---------------------------------------------------------------------------------------------------------------

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.redis-distributed-lock")
    public static class DistributedLockProps {

        private boolean enabled = true;
        private Mode mode = Mode.SINGLE;
        private String keyScope = "";
        private JedisConfig jedis = new JedisConfig();

        // ----------------------------------------------------------------------------------------------------------------

        @Getter
        @Setter
        public static class JedisConfig {
            private String host = "localhost";
            private int port = 6379;
            private int timeout = 3000;
            private String password = "";

            private String masterName = "master";
            private String[] sentinels;
        }
    }

    public enum Mode {

        /**
         * 单节点模式
         */
        SINGLE,

        /**
         * 哨兵模式
         */
        SENTINEL,
    }
}
