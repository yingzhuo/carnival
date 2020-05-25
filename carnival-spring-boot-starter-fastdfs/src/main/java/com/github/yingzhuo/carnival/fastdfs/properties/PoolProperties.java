/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.properties;

import com.github.yingzhuo.carnival.fastdfs.domain.conn.Connection;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * 连接池配置
 *
 * @author tobato
 * @author 应卓
 */
@ConfigurationProperties(prefix = "carnival.fastdfs.pool")
public class PoolProperties extends GenericKeyedObjectPoolConfig<Connection> implements Serializable, InitializingBean {

    /**
     * 从池中借出的对象的最大数目
     */
    public static final int FDFS_MAX_TOTAL = -1;

    /**
     * 每个Key最大连接数
     */
    public static final int FDFS_MAX_TOTAL_PER_KEY = 50;

    /**
     * 每个key对应的连接池最大空闲连接数
     */
    public static final int FDFS_MAX_IDLE_PER_KEY = 10;

    /**
     * 每个key对应的连接池最小空闲连接数
     */
    public static final int FDFS_MIN_IDLE_PER_KEY = 5;

    /**
     * 在空闲时检查有效性, 默认true
     */
    public static final boolean FDFS_TEST_WHILE_IDLE = false;

    /**
     * 连接耗尽时是否阻塞(默认true)
     * false报异常,true阻塞直到超时
     */
    public static final boolean FDFS_BLOCK_WHEN_EXHAUSTED = true;

    /**
     * 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted)
     * 如果超时就抛异常,小于零:阻塞不确定的时间,默认-1
     */
    public static final long FDFS_MAX_WAIT_MILLIS = 1000 * 5;

    /**
     * 连接空闲的最小时间，达到此值后空闲连接将可能会被移除。负值(-1)表示不移除
     */
    public static final long FDFS_MIN_EVICTABLE_IDLE_TIME_MILLIS = 60 * 30 * 1000;

    /**
     * 逐出扫描的时间间隔(毫秒) 每过60秒进行一次后台对象清理的行动
     * 如果为负数,则不运行逐出线程, 默认-1
     */
    public static final long FDFS_TIME_BETWEEN_EVICTION_RUNS_MILLIS = 60 * 1000;

    /**
     * 对于“空闲链接”检测线程而言，每次检测的链接资源的个数,默认3
     * －1表示清理时检查所有线程
     */
    public static final int FDFS_NUM_TESTS_PER_EVICTION_RUN = 3;

    /**
     * 连接池是否开放JMX
     */
    public static final boolean FDFS_JMX_ENABLED = false;

    /**
     * 默认JMX域名
     */
    public static final String FDFS_JMX_NAME_BASE = null;

    /**
     * 默认JMX prefix名称
     */
    public static final String FDFS_JMX_NAME_PREFIX = null;

    /**
     * 连接空闲的最小时间，达到此值后空闲链接将会被移除，且保留“minIdle”个空闲连接数。默认为-1.
     * 如果设置了FDFS_MIN_EVICTABLE_IDLE_TIME_MILLIS，则此参数被忽略
     */
    public static final int FDFS_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS = -1;

    /**
     * 默认值false
     */
    public static final boolean FDFS_TEST_ON_CREATE = false;

    /**
     * 向调用者输出“链接”资源时，是否检测有效性，如果无效则从连接池中移除，
     * 并尝试获取继续获取。默认为false。建议保持默认值.
     */
    public static final boolean FDFS_TEST_ON_BORROW = true;

    public PoolProperties() {
        // 从池中借出的对象的最大数目
        setMaxTotal(FDFS_MAX_TOTAL);
        // 在空闲时检查有效性
        setTestWhileIdle(FDFS_TEST_WHILE_IDLE);
        // 连接耗尽时是否阻塞(默认true)
        setBlockWhenExhausted(FDFS_BLOCK_WHEN_EXHAUSTED);
        // 获取连接时的最大等待5秒
        setMaxWaitMillis(FDFS_MAX_WAIT_MILLIS);
        // 每个key对应的池最大连接数
        setMaxTotalPerKey(FDFS_MAX_TOTAL_PER_KEY);
        // 每个key对应的连接池最大空闲连接数
        setMaxIdlePerKey(FDFS_MAX_IDLE_PER_KEY);
        // 每个key对应的连接池最小空闲连接数
        setMinIdlePerKey(FDFS_MIN_IDLE_PER_KEY);
        // 视休眠时间超过了180秒的对象为过期
        setMinEvictableIdleTimeMillis(FDFS_MIN_EVICTABLE_IDLE_TIME_MILLIS);
        // 每过60秒进行一次后台对象清理的行动
        setTimeBetweenEvictionRunsMillis(FDFS_TIME_BETWEEN_EVICTION_RUNS_MILLIS);
        // 清理时候检查所有线程
        setNumTestsPerEvictionRun(FDFS_NUM_TESTS_PER_EVICTION_RUN);
        // 连接空闲的最小时间，达到此值后空闲链接将会被移除，且保留“minIdle”个空闲连接数
        setSoftMinEvictableIdleTimeMillis(FDFS_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
        // 创建连接时进行连接测试
        setTestOnCreate(FDFS_TEST_ON_CREATE);
        // 向调用者输出“连接”资源时，是否检测有效性
        setTestOnBorrow(FDFS_TEST_ON_BORROW);
        // 配置jmx
        setJmxEnabled(FDFS_JMX_ENABLED);
        setJmxNameBase(FDFS_JMX_NAME_BASE);
        setJmxNamePrefix(FDFS_JMX_NAME_PREFIX);
    }

    @Override
    public void afterPropertiesSet() {
    }

}
