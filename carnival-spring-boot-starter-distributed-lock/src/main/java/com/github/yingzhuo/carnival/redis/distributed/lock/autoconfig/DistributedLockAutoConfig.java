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

import com.github.yingzhuo.carnival.redis.distributed.lock.JedisCommandsFinder;
import com.github.yingzhuo.carnival.redis.distributed.lock.RequestIdFactory;
import com.github.yingzhuo.carnival.redis.distributed.lock.impl.DefaultRequestIdFactory;
import com.github.yingzhuo.carnival.redis.distributed.lock.impl.SimpleJedisCommandsFinder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 应卓
 */
@Slf4j
@EnableConfigurationProperties(DistributedLockAutoConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.distributed-lock", name = "enabled", havingValue = "true", matchIfMissing = true)
public class DistributedLockAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public JedisCommandsFinder jedisCommandsFinder(Props props) {

        // 单例模式
        if (props.getMode() == Mode.SINGLE) {
            final JedisPool pool = new JedisPool(
                    createPoolConfig(props.pool),
                    props.single.host,
                    props.single.port,
                    props.single.timeout,
                    props.single.password);
            return new SimpleJedisCommandsFinder(pool);
        }

        // 哨兵模式
        if (props.getMode() == Mode.SENTINEL) {
            JedisPoolConfig config = createPoolConfig(props.pool);

            JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(
                    props.sentinel.masterName,
                    Arrays.stream(props.sentinel.nodes.split(",")).collect(Collectors.toSet()),
                    config,
                    props.sentinel.timeout,
                    props.sentinel.password
            );

            return new SimpleJedisCommandsFinder(jedisSentinelPool);
        }

        // 集群模式
        if (props.getMode() == Mode.CLUSTER) {
            JedisPoolConfig config = createPoolConfig(props.pool);

            Set<String> redisSets = Stream.of(props.cluster.nodes.split(",")).collect(Collectors.toSet());
            Set<HostAndPort> nodes = new HashSet<>();
            for (String str : redisSets) {
                String[] nodeInfo = str.split(":");
                nodes.add(new HostAndPort(nodeInfo[0], Integer.parseInt(nodeInfo[1])));
            }

            JedisCluster jedisCluster = new JedisCluster(nodes,
                    props.cluster.connectionTimeoutMillis,
                    props.cluster.soTimeoutMillis,
                    props.cluster.maxAttempts,
                    props.cluster.password,
                    config);

            return new SimpleJedisCommandsFinder(jedisCluster);
        }

        throw new AssertionError();
    }

    private JedisPoolConfig createPoolConfig(PoolProps pp) {
        final JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(pp.maxIdle);
        config.setMaxTotal(pp.maxTotal);
        config.setMaxWaitMillis(pp.maxWaitMillis);
        config.setTestOnBorrow(pp.testOnBorrow);
        return config;
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestIdFactory requestIdFactory() {
        return new DefaultRequestIdFactory();
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.distributed-lock")
    static class Props {

        private boolean enable = true;
        private Mode mode = Mode.SINGLE;
        private PoolProps pool = new PoolProps();
        private SingleProps single = new SingleProps();
        private SentinelProps sentinel = new SentinelProps();
        private ClusterProps cluster = new ClusterProps();
    }

    @Getter
    @Setter
    static class PoolProps {

        private int maxIdle = 8;
        private int maxTotal = 8;
        private long maxWaitMillis = -1L;
        private boolean testOnBorrow = false;
    }

    @Getter
    @Setter
    static class SingleProps {

        private String host = "localhost";
        private int port = 6379;
        private String password = null;
        private int timeout = 10000;
    }

    @Getter
    @Setter
    static class SentinelProps {

        private String masterName;
        private String password = null;
        private int timeout = 10000;
        private String nodes;
    }

    @Getter
    @Setter
    static class ClusterProps {
        private String password = null;
        private int timeout = 10000;
        private String nodes;
        private int connectionTimeoutMillis = 10000;
        private int soTimeoutMillis = 10000;
        private int maxAttempts = 3;
    }

    enum Mode {

        /**
         * 单节点模式
         */
        SINGLE,

        /**
         * 哨兵模式
         */
        SENTINEL,

        /**
         * 集群模式
         */
        CLUSTER
    }
}
