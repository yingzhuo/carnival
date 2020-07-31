/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.actuator.stateprobe;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author 应卓
 * @since 1.6.32
 */
abstract class AbstractStateEndpoint implements ApplicationContextAware {

    protected ApplicationContext applicationContext;

    @Override
    public final void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    protected final boolean enable(String value) {

        if ("ng".equalsIgnoreCase(value) ||
                "off".equalsIgnoreCase(value) ||
                "false".equalsIgnoreCase(value) ||
                "0".equals(value)
        ) return false;

        if ("ok".equalsIgnoreCase(value) ||
                "on".equalsIgnoreCase(value) ||
                "true".equalsIgnoreCase(value) ||
                "1".equals(value)
        ) return true;

        throw new IllegalArgumentException();
    }

}
