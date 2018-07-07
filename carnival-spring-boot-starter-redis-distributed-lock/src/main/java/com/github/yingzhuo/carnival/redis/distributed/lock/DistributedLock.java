/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.redis.distributed.lock;

import com.github.yingzhuo.carnival.redis.distributed.lock.autoconfig.DistributedLockAutoCnf;
import com.github.yingzhuo.carnival.redis.distributed.lock.request.RequestIdFactory;
import com.github.yingzhuo.carnival.spring.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;

/**
 * Redis实现分布式锁
 *
 * @author 应卓
 */
public final class DistributedLock {

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedLock.class);

    private DistributedLock() {
        super();
    }

    public static boolean lock(String key) {

        Assert.hasText(key, "key is null or blank");

        final JedisPool jedisPool = SpringUtils.getBean(JedisPool.class);
        final DistributedLockAutoCnf.DistributedLockProps props = SpringUtils.getBean(DistributedLockAutoCnf.DistributedLockProps.class);
        final RequestIdFactory requestIdFactory = SpringUtils.getBean(RequestIdFactory.class);
        final String effKey = props.getKeyScope() + key;
        final String requestId = requestIdFactory.create(SpringUtils.getSpringId(), Thread.currentThread().getId());

        try (Jedis jedis = jedisPool.getResource()) {

            String result = jedis.set(
                    effKey,
                    requestId,
                    SET_IF_NOT_EXIST,
                    SET_WITH_EXPIRE_TIME,
                    props.getKeyExpireInMillis());

            return LOCK_SUCCESS.equals(result);
        }
    }

    public static boolean release(String key) {

        Assert.hasText(key, "key is null or blank");

        final JedisPool jedisPool = SpringUtils.getBean(JedisPool.class);
        final DistributedLockAutoCnf.DistributedLockProps props = SpringUtils.getBean(DistributedLockAutoCnf.DistributedLockProps.class);
        final RequestIdFactory requestIdFactory = SpringUtils.getBean(RequestIdFactory.class);
        final String effKey = props.getKeyScope() + key;
        final String requestId = requestIdFactory.create(SpringUtils.getSpringId(), Thread.currentThread().getId());

        try (Jedis jedis = jedisPool.getResource()) {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList(effKey), Collections.singletonList(requestId));
            return RELEASE_SUCCESS.equals(result);
        }
    }

    public static boolean lock(long key) {
        return lock(String.valueOf(key));
    }

    public static boolean release(long key) {
        return release(String.valueOf(key));
    }

    public static boolean lock(int key) {
        return lock(String.valueOf(key));
    }

    public static boolean release(int key) {
        return release(String.valueOf(key));
    }

}
