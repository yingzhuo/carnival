/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.redis.support;

import com.github.yingzhuo.carnival.spring.SpringUtils;
import org.springframework.beans.factory.InitializingBean;

import java.time.Duration;

/**
 * @author 应卓
 * @since 1.6.19
 */
public abstract class AbstractLockBean implements InitializingBean {

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
            return String.valueOf(defaultMax.getSeconds());
        } else {
            return String.valueOf(ttl.getSeconds());
        }
    }

    @Override
    public void afterPropertiesSet() {
        if (prefix == null) prefix = "";
        if (suffix == null) suffix = "";
        if (defaultMax == null) defaultMax = Duration.ofSeconds(10);
    }

    public final void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public final void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public final void setDefaultMax(Duration defaultMax) {
        this.defaultMax = defaultMax;
    }

}
