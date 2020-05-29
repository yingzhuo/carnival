/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.redis.lock;

import com.github.yingzhuo.carnival.spring.SpringUtils;
import org.springframework.beans.factory.InitializingBean;

import java.time.Duration;

/**
 * @author 应卓
 * @since 1.6.9
 */
public abstract class AbstractRedisLockBean implements RedisLockBean, InitializingBean {

    private String prefix = "";
    private String suffix = "";
    private Duration defaultMax;

    protected final String generateLockKey(String key) {
        return prefix + key + suffix;
    }

    protected final String generateLockValue() {
        return SpringUtils.getSpringId() + "." + Thread.currentThread().getId();
    }

    protected final String getMaxOfDefault(Duration ttl) {
        if (ttl == null) {
            return defaultMax.getSeconds() + "";
        } else {
            return ttl.getSeconds() + "";
        }
    }

    @Override
    public void afterPropertiesSet() {
        if (prefix == null) prefix = "";
        if (suffix == null) suffix = "";
        if (defaultMax == null) defaultMax = Duration.ofSeconds(10);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setDefaultMax(Duration defaultMax) {
        this.defaultMax = defaultMax;
    }

}
