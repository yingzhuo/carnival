/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jedis.autoconfig;

import com.github.yingzhuo.carnival.jedis.JedisCommandsHolder;
import com.github.yingzhuo.carnival.jedis.core.DefaultJedisCommandsHolder;
import com.github.yingzhuo.carnival.jedis.props.JedisStringToHostPortConverter;
import com.github.yingzhuo.carnival.jedis.props.Props;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * @author 应卓
 * @since 1.6.7
 */
@Lazy(false)
@EnableConfigurationProperties(Props.class)
@ConditionalOnProperty(prefix = "carnival.jedis", name = "enabled", havingValue = "true", matchIfMissing = true)
public class JedisCoreAutoConfig {

    @Bean
    @ConfigurationPropertiesBinding
    public JedisStringToHostPortConverter jedisStringToHostPortConverter() {
        return new JedisStringToHostPortConverter();
    }

    @Bean
    public JedisCommandsHolder jedisCommandsHolder(Props props) {
        final JedisPoolConfig poolConfig = createPoolConfig(props.getPool());

        switch (props.getMode()) {
            // 单节点
            case SINGLE:
                final JedisPool pool = new JedisPool(
                        poolConfig,
                        props.getSingle().getNode().getHost(),
                        props.getSingle().getNode().getPort(),
                        props.getSingle().getTimeout(),
                        props.getSingle().getPassword());
                return new DefaultJedisCommandsHolder(pool);

            // 哨兵
            case SENTINEL:
                final JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(
                        props.getSentinel().getMasterName(),
                        props.getSentinel().getNodes().stream().map(HostAndPort::toString).collect(Collectors.toSet()),
                        poolConfig,
                        props.getSentinel().getTimeout(),
                        props.getSentinel().getPassword()
                );
                return new DefaultJedisCommandsHolder(jedisSentinelPool);

            // 集群
            case CLUSTER:
                final JedisCluster jedisCluster = new JedisCluster(
                        new HashSet<>(props.getCluster().getNodes()),
                        props.getCluster().getConnectionTimeoutMillis(),
                        props.getCluster().getSoTimeoutMillis(),
                        props.getCluster().getMaxAttempts(),
                        props.getCluster().getPassword(),
                        poolConfig);

                return new DefaultJedisCommandsHolder(jedisCluster);

            default:
                throw new AssertionError();
        }
    }

    private JedisPoolConfig createPoolConfig(Props.Pool pool) {

        // 配置详解 https://yq.aliyun.com/articles/236383

        final JedisPoolConfig config = new JedisPoolConfig();

        // 资源设置和使用
        config.setMaxTotal(pool.getMaxTotal());
        config.setMaxIdle(pool.getMaxIdle());
        config.setBlockWhenExhausted(pool.isBlockWhenExhausted());
        config.setMaxWaitMillis(pool.getMaxWaitMillis());
        config.setTestOnBorrow(pool.isTestOnBorrow());
        config.setTestOnCreate(pool.isTestOnCreate());
        config.setTestOnReturn(pool.isTestOnReturn());
        config.setJmxEnabled(pool.isJmxEnabled());

        // 空闲资源监测
        config.setTestWhileIdle(pool.isTestWhileIdle());
        config.setTimeBetweenEvictionRunsMillis(pool.getTimeBetweenEvictionRunsMillis());
        config.setMinEvictableIdleTimeMillis(pool.getMinEvictableIdleTimeMillis());
        config.setNumTestsPerEvictionRun(pool.getNumTestsPerEvictionRun());
        return config;
    }

}
