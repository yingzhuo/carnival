package com.github.yingzhuo.carnival.nsq.producer;

import org.junit.Test;

public class TestAbstractProducer {

    private AbstractProducer producer = new AbstractProducer() {
        @Override
        public void mpub(String topic, Messages messages) {

        }

        @Override
        public void pub(String topic, String message, long defer) {

        }
    };

    @Test
    public void testDoSend() {
        producer.doPub("http","192.168.99.114", 4151, "test", "123", -1);
    }

}
