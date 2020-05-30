/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.spring;

import org.springframework.context.ApplicationEvent;

/**
 * @author 应卓
 * @see SpringUtils
 * @since 1.4.5
 */
public final class EventUtils {

    private EventUtils() {
    }

    public static void publish(ApplicationEvent event) {
        SpringUtils.getApplicationEventPublisher().publishEvent(event);
    }

    public static void publish(Object event) {
        SpringUtils.getApplicationEventPublisher().publishEvent(event);
    }

}
