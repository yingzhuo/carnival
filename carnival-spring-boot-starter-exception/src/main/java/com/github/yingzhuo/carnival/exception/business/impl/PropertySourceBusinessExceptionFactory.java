/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.exception.business.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Optional;
import java.util.TreeMap;

/**
 * @author 应卓
 * @since 1.4.1
 */
public class PropertySourceBusinessExceptionFactory extends AbstractBusinessExceptionFactory {

    private final Env env;

    public PropertySourceBusinessExceptionFactory(Env env) {
        this.env = env;
    }

    @Override
    protected Optional<String> getMessage(String code) {
        return Optional.ofNullable(env.get(code));
    }

    @Slf4j
    @ConfigurationProperties(prefix = "business.exception")
    public static class Env extends HashMap<String, String> implements InitializingBean {

        @Override
        public void afterPropertiesSet() {
            if (log.isDebugEnabled() && !isEmpty()) {
                val sorted = new TreeMap<>(this);
                log.debug("business-exception: ");
                for (String key : sorted.keySet()) {
                    val value = sorted.get(key);
                    log.debug("business.exception.{}={}", key, value);
                }
            }
        }
    }

}
