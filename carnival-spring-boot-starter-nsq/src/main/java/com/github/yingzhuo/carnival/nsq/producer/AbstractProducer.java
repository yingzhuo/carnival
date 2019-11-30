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

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
abstract class AbstractProducer implements Producer {

    private final RestTemplate restTemplate = new RestTemplateBuilder()
            .errorHandler(new DefaultResponseErrorHandler() {
                @Override
                public void handleError(URI url, HttpMethod method, ClientHttpResponse response) {
                }
            })
            .build();


    protected final void doPub(String protocol, String host, int httpPort, String topic, String message, long defer) {

        if (message == null) {
            return;
        }

        val builder = UriComponentsBuilder.newInstance()
                .scheme(protocol)
                .host(host)
                .port(httpPort)
                .path("/pub")
                .queryParam("topic", topic);

        if (defer >= 0) {
            builder.queryParam("defer", defer);
        }

        val url = builder.toUriString();
        val responseEntity = restTemplate.postForEntity(url, new HttpEntity<>(message), String.class);

        if (responseEntity.getStatusCodeValue() != 200) {
            log.debug("fail to send message");
        }
    }

    protected final void doMpub(String protocol, String host, int httpPort, String topic, Messages messages) {

        if (messages == null || messages.isEmpty()) {
            return;
        }

        val url = UriComponentsBuilder.newInstance()
                .scheme(protocol)
                .host(host)
                .port(httpPort)
                .path("/mpub")
                .queryParam("binary", false)
                .queryParam("topic", topic)
                .toUriString();
        val responseEntity = restTemplate.postForEntity(url, new HttpEntity<>(messages.toString()), String.class);

        if (responseEntity.getStatusCodeValue() != 200) {
            log.debug("fail to send messages");
        }
    }

}
