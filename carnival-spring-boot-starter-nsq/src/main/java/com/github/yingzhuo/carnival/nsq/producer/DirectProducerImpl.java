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

import com.github.yingzhuo.carnival.nsq.config.ProducerConfig;

/**
 * @author 应卓
 * @since 1.3.1
 */
public class DirectProducerImpl extends AbstractProducer implements Producer {

    private ProducerConfig config;

    public DirectProducerImpl(ProducerConfig config) {
        this.config = config;
    }

    @Override
    public void pub(String topic, String message, long defer) {
        super.doPub(config.getProtocol().toString(),
                config.getHost(),
                config.getHttpPort(),
                topic,
                message,
                defer);
    }

    @Override
    public void mpub(String topic, Messages messages) {
        super.doMpub(
                config.getProtocol().toString(),
                config.getHost(),
                config.getHttpPort(),
                topic,
                messages);
    }

}
