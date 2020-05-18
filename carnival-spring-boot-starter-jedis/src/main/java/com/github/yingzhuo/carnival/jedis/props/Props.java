/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jedis.props;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;
import redis.clients.jedis.HostAndPort;

import java.io.Serializable;
import java.util.List;

/**
 * @author 应卓
 * @since 1.6.7
 */
@Slf4j
@Getter
@Setter
@ConfigurationProperties(prefix = "carnival.jedis")
public class Props implements Serializable, InitializingBean {

    private boolean enabled = true;
    private Mode mode = Mode.SINGLE;
    private Pool pool = new Pool();
    private Single single = new Single();
    private Sentinel sentinel = new Sentinel();
    private Cluster cluster = new Cluster();

    @Override
    public void afterPropertiesSet() {
        if (enabled) {
            pool.afterPropertiesSet();

            switch (mode) {
                case SINGLE:
                    single.afterPropertiesSet();
                    return;
                case SENTINEL:
                    sentinel.afterPropertiesSet();
                    return;
                case CLUSTER:
                    cluster.afterPropertiesSet();
                    return;
                default:
                    throw new AssertionError();
            }
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

        /**
         * 集群模式
         */
        CLUSTER
    }

    @Getter
    @Setter
    public static class Pool implements Serializable, InitializingBean {

        // 资源池中最大连接数
        private int maxTotal = 8;

        // 资源池允许最大空闲的连接数
        private int maxIdle = 8;

        // 当资源池用尽后，调用者是否要等待。
        // 只有当为true时，下面的maxWaitMillis才会生效
        private boolean blockWhenExhausted = true;

        // 等待资源超时时间
        private long maxWaitMillis = -1L;

        // 向资源池借用连接时是否做连接有效性检测(ping)
        private boolean testOnBorrow = false;

        // 向资源池归还连接时是否做连接有效性检测(ping)
        private boolean testOnReturn = false;

        // 创建新的连接时是否做连接有效性检测(ping)
        private boolean testOnCreate = false;

        // jmx监控
        private boolean jmxEnabled = false;

        // 是否开启空闲资源监测
        private boolean testWhileIdle = false;

        // 空闲资源的检测周期
        private long timeBetweenEvictionRunsMillis = 1800000;

        // 资源池中资源最小空闲时间，达到此值后空闲资源将被移除
        private long minEvictableIdleTimeMillis = 1000 * 60 * 30;

        // 做空闲资源检测时，每次的采样数
        private int numTestsPerEvictionRun = 3;

        @Override
        public void afterPropertiesSet() {
            // NOP
        }
    }

    @Getter
    @Setter
    public static class Single implements Serializable, InitializingBean {
        private HostAndPort node;
        private String password = null;
        private int timeout = 10000;

        @Override
        public void afterPropertiesSet() {
            Assert.notNull(node, (String) null);
        }
    }

    @Getter
    @Setter
    public static class Sentinel implements Serializable, InitializingBean {
        private String masterName;
        private String password = null;
        private int timeout = 10000;
        private List<HostAndPort> nodes;

        @Override
        public void afterPropertiesSet() {
            Assert.notNull(nodes, (String) null);
            Assert.isTrue(nodes.size() >= 1, (String) null);
        }
    }

    @Getter
    @Setter
    public static class Cluster implements Serializable, InitializingBean {
        private String password = null;
        private List<HostAndPort> nodes;
        private int connectionTimeoutMillis = 10000;
        private int soTimeoutMillis = 10000;
        private int maxAttempts = 3;

        @Override
        public void afterPropertiesSet() {
            Assert.notNull(nodes, (String) null);
            Assert.isTrue(nodes.size() >= 1, (String) null);
        }
    }

}
