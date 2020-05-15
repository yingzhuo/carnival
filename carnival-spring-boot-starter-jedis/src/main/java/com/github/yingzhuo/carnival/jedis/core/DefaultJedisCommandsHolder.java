/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jedis.core;

import com.github.yingzhuo.carnival.jedis.JedisCommandsHolder;
import org.springframework.beans.factory.DisposableBean;
import redis.clients.jedis.*;

import java.util.Objects;

/**
 * @author 应卓
 * @since 1.6.7
 */
public class DefaultJedisCommandsHolder implements JedisCommandsHolder, DisposableBean {

    private final JedisPool jedisPool;
    private final JedisSentinelPool jedisSentinelPool;
    private final JedisCluster jedisCluster;

    private DefaultJedisCommandsHolder(JedisPool jedisPool, JedisSentinelPool jedisSentinelPool, JedisCluster jedisCluster) {
        this.jedisPool = jedisPool;
        this.jedisSentinelPool = jedisSentinelPool;
        this.jedisCluster = jedisCluster;
    }

    public DefaultJedisCommandsHolder(JedisPool pool) {
        this(Objects.requireNonNull(pool), null, null);
    }

    public DefaultJedisCommandsHolder(JedisSentinelPool pool) {
        this(null, Objects.requireNonNull(pool), null);
    }

    public DefaultJedisCommandsHolder(JedisCluster cluster) {
        this(null, null, Objects.requireNonNull(cluster));
    }

    @Override
    public JedisCommands get() {
        if (jedisPool != null) {
            return jedisPool.getResource();
        } else if (jedisSentinelPool != null) {
            return jedisSentinelPool.getResource();
        } else {
            return jedisCluster;
        }
    }

    @Override
    public void close(JedisCommands commands) {
        if (commands != null) {
            if (commands instanceof Jedis) {
                ((Jedis) commands).close();
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        if (jedisPool != null) {
            jedisPool.close();
        }

        if (jedisSentinelPool != null) {
            jedisSentinelPool.close();
        }

        if (jedisCluster != null) {
            jedisCluster.close();
        }
    }

}
