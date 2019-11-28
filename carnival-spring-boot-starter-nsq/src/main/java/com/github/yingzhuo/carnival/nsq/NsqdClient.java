/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.nsq;

import com.github.yingzhuo.carnival.nsq.message.Messages;
import com.github.yingzhuo.carnival.nsq.model.Info;

import java.util.concurrent.TimeUnit;

/**
 * @author 应卓
 * @since 1.3.1
 */
public interface NsqdClient {

    /**
     * Version information
     *
     * @return the information
     */
    public Info info();

    /**
     * Monitoring endpoint
     *
     * @return status string ("ok", "unhealthy", etc)
     */
    public String ping();

    /**
     * Publish a message
     *
     * @param topic   the topic to publish to
     * @param message the message
     * @param defer   the time in ms to delay message delivery
     */
    public void pub(String topic, String message, long defer);

    /**
     * Publish a message
     *
     * @param topic         the topic to publish to
     * @param message       the message
     * @param defer         the time to delay message delivery
     * @param deferTimeUnit the time unit of param defer
     */
    public default void pub(String topic, String message, long defer, TimeUnit deferTimeUnit) {
        pub(topic, message, deferTimeUnit.toMillis(defer));
    }

    /**
     * Publish a message
     *
     * @param topic   the topic to publish to
     * @param message the message
     */
    public default void pub(String topic, String message) {
        pub(topic, message, -1);
    }

    /**
     * Publish multiple messages in one roundtrip
     *
     * @param topic  the topic to publish to
     * @param binary to enable binary mode if true
     */
    public void mpub(String topic, String message, boolean binary);

    /**
     * Publish multiple messages in one roundtrip
     *
     * @param topic   the topic to publish to
     * @param message the message
     */
    public default void mpub(String topic, String message) {
        mpub(topic, message, false);
    }

    /**
     * Publish multiple messages in one roundtrip
     *
     * @param topic    the topic to publish to
     * @param messages the messages
     */
    public default void mpub(String topic, Messages messages) {
        mpub(topic, messages.toString(), false);
    }

    /**
     * Create a topic
     *
     * @param topic the topic to create
     */
    public void createTopic(String topic);

    /**
     * Delete a topic
     *
     * @param topic the topic to delete
     */
    public void deleteTopic(String topic);

    /**
     * Create a channel for an existing topic
     *
     * @param topic   the existing topic
     * @param channel the channel to create
     */
    public void createChannel(String topic, String channel);

    /**
     * Delete an existing channel on an existing topic
     *
     * @param topic   the existing topic
     * @param channel the channel to delete
     */
    public void deleteChannel(String topic, String channel);

    /**
     * Empty all the queued messages (in-memory and disk) for an existing topic
     *
     * @param topic the existing topic to empty
     */
    public void emptyTopic(String topic);

    /**
     * Empty all the queued messages (in-memory and disk) for an existing topic
     *
     * @param topic   the existing topic
     * @param channel the existing channel to empty
     */
    public void emptyChannel(String topic, String channel);

    /**
     * Pause message flow to all channels on an existing topic (messages will queue at topic)
     *
     * @param topic the existing topic
     */
    public void pauseTopic(String topic);

    /**
     * Resume message flow to channels of an existing, paused, topic
     *
     * @param topic the existing topic to resume
     */
    public void unpauseTopic(String topic);

    /**
     * Pause message flow to consumers of an existing channel (messages will queue at channel)
     *
     * @param topic   the existing topic
     * @param channel the existing channel to pause
     */
    public void pauseChannel(String topic, String channel);

    /**
     * Resume message flow to consumers of an existing, paused, channel
     *
     * @param topic   the existing topic
     * @param channel the existing channel to resume
     */
    public void unpauseChannel(String topic, String channel);
}
