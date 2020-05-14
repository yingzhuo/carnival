/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.norepeated.autoconfig;

import com.github.yingzhuo.carnival.exception.ExceptionTransformer;
import com.github.yingzhuo.carnival.restful.norepeated.NoRepeatedConfigurer;
import com.github.yingzhuo.carnival.restful.norepeated.core.NoRepeatedInterceptor;
import com.github.yingzhuo.carnival.restful.norepeated.factory.NoRepeatedTokenFactory;
import com.github.yingzhuo.carnival.restful.norepeated.factory.NoRepeatedTokenFactoryImpl;
import com.github.yingzhuo.carnival.restful.norepeated.parser.NoRepeatedTokenParser;
import com.github.yingzhuo.carnival.restful.norepeated.support.JedisCommandsFinder;
import com.github.yingzhuo.carnival.restful.norepeated.support.JedisCommandsFinderImpl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import redis.clients.jedis.*;

import java.io.Serializable;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 应卓
 * @since 1.6.7
 */
@Lazy(false)
@EnableConfigurationProperties(NoRepeatedCoreAutoConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.no-repeated", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnWebApplication
public class NoRepeatedCoreAutoConfig implements WebMvcConfigurer {

    @Autowired(required = false)
    private ExceptionTransformer injectedExceptionTransformer;

    @Autowired(required = false)
    private NoRepeatedTokenParser injectedNoRepeatedTokenParser;

    @Autowired(required = false)
    private NoRepeatedConfigurer configurer = new NoRepeatedConfigurer() {
    };

    @Bean
    public NoRepeatedTokenFactory noRepeatedTokenFactory(Props props) {
        return new NoRepeatedTokenFactoryImpl(
                props.getToken().getTtl(),
                props.getToken().getPrefix(),
                props.getToken().getSuffix()
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public JedisCommandsFinder noRepeatedJedisCommandsFinder(Props props) {
        // 单例模式
        if (props.getMode() == Mode.SINGLE) {
            final JedisPool pool = new JedisPool(
                    createPoolConfig(props.pool),
                    props.single.host,
                    props.single.port,
                    props.single.timeout,
                    props.single.password);
            return new JedisCommandsFinderImpl(pool);
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

            return new JedisCommandsFinderImpl(jedisSentinelPool);
        }

        // 集群模式
        if (props.getMode() == Mode.CLUSTER) {
            JedisPoolConfig config = createPoolConfig(props.pool);

            Set<String> redisSet = Stream.of(props.cluster.nodes.split(",")).collect(Collectors.toSet());
            Set<HostAndPort> nodes = new HashSet<>();
            for (String str : redisSet) {
                String[] nodeInfo = str.split(":");
                nodes.add(new HostAndPort(nodeInfo[0], Integer.parseInt(nodeInfo[1])));
            }

            JedisCluster jedisCluster = new JedisCluster(
                    nodes,
                    props.cluster.connectionTimeoutMillis,
                    props.cluster.soTimeoutMillis,
                    props.cluster.maxAttempts,
                    props.cluster.password,
                    config);

            return new JedisCommandsFinderImpl(jedisCluster);
        }

        throw new AssertionError();
    }

    private JedisPoolConfig createPoolConfig(PoolProps pp) {

        // 配置详解 https://yq.aliyun.com/articles/236383

        final JedisPoolConfig config = new JedisPoolConfig();

        // 资源设置和使用
        config.setMaxTotal(pp.maxTotal);
        config.setMaxIdle(pp.maxIdle);
        config.setBlockWhenExhausted(pp.blockWhenExhausted);
        config.setMaxWaitMillis(pp.maxWaitMillis);
        config.setTestOnBorrow(pp.testOnBorrow);
        config.setTestOnCreate(pp.testOnCreate);
        config.setTestOnReturn(pp.testOnReturn);
        config.setJmxEnabled(pp.jmxEnabled);

        // 空闲资源监测
        config.setTestWhileIdle(pp.testWhileIdle);
        config.setTimeBetweenEvictionRunsMillis(pp.timeBetweenEvictionRunsMillis);
        config.setMinEvictableIdleTimeMillis(pp.minEvictableIdleTimeMillis);
        config.setNumTestsPerEvictionRun(pp.numTestsPerEvictionRun);
        return config;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        final NoRepeatedInterceptor interceptor = new NoRepeatedInterceptor();
        interceptor.setExceptionTransformer(getExceptionTransformer());
        interceptor.setTokenParser(getNoRepeatedTokenParser());

        registry.addInterceptor(interceptor)
                .addPathPatterns(configurer.getPathPatterns())
                .order(configurer.getOrder());
    }

    private NoRepeatedTokenParser getNoRepeatedTokenParser() {
        if (injectedNoRepeatedTokenParser != null) {
            return injectedNoRepeatedTokenParser;
        }
        return configurer.getNoRepeatedTokenParser();
    }

    private ExceptionTransformer getExceptionTransformer() {
        if (injectedExceptionTransformer != null) {
            return injectedExceptionTransformer;
        }
        return configurer.getExceptionTransformer();
    }

    // ----------------------------------------------------------------------------------------------------------------

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

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.no-repeated")
    public static class Props implements Serializable {
        private boolean enable = true;
        private Mode mode = Mode.SINGLE;
        private TokenProps token = new TokenProps();
        private PoolProps pool = new PoolProps();
        private SingleProps single = new SingleProps();
        private SentinelProps sentinel = new SentinelProps();
        private ClusterProps cluster = new ClusterProps();
    }

    @Getter
    @Setter
    static class TokenProps implements Serializable {
        @DurationUnit(ChronoUnit.SECONDS)
        private Duration ttl = Duration.ofMinutes(5L);
        private String prefix = "no-repeated-token-";
        private String suffix = "";
    }

    @Getter
    @Setter
    static class PoolProps implements Serializable {

        /*
         * 资源设置和使用
         */

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

        /*
         * 空闲资源监测
         */

        // 是否开启空闲资源监测
        private boolean testWhileIdle = false;

        // 空闲资源的检测周期
        private long timeBetweenEvictionRunsMillis = 1000 * 60 * 30;

        // 资源池中资源最小空闲时间，达到此值后空闲资源将被移除
        private long minEvictableIdleTimeMillis = 1000 * 60 * 30;

        // 做空闲资源检测时，每次的采样数
        private int numTestsPerEvictionRun = 3;
    }

    @Getter
    @Setter
    static class SingleProps implements Serializable {
        private String host = "localhost";
        private int port = 6379;
        private String password = null;
        private int timeout = 10000;
    }

    @Getter
    @Setter
    static class SentinelProps implements Serializable {
        private String masterName;
        private String password = null;
        private int timeout = 10000;
        private String nodes;
    }

    @Getter
    @Setter
    static class ClusterProps implements Serializable {
        private String password = null;
        private String nodes;
        private int connectionTimeoutMillis = 10000;
        private int soTimeoutMillis = 10000;
        private int maxAttempts = 3;
    }

}
