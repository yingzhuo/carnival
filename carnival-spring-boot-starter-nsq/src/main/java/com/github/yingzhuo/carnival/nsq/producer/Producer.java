/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.nsq.producer;

import java.util.concurrent.TimeUnit;

/**
 * @author 应卓
 * @since 1.3.1
 */
public interface Producer {

    public void pub(String topic, String message, long defer);

    public default void pub(String topic, String message) {
        pub(topic, message, -1);
    }

    public default void pub(String topic, String message, long defer, TimeUnit deferTimeUnit) {
        pub(topic, message, deferTimeUnit.toMillis(defer));
    }

    public void mpub(String topic, Messages messages);

    public default void mpub(String topic, String... messages) {
        mpub(topic, Messages.of(messages));
    }

}
