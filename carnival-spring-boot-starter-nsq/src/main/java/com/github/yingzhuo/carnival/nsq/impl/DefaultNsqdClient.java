/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.nsq.impl;

import com.github.yingzhuo.carnival.nsq.NsqdClient;
import com.github.yingzhuo.carnival.nsq.model.Info;
import com.github.yingzhuo.carnival.nsq.node.NsqdNode;
import com.github.yingzhuo.carnival.nsq.selector.NsqdNodeSelector;
import lombok.val;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author 应卓
 * @since 1.3.1
 */
public class DefaultNsqdClient extends AbstractClient implements NsqdClient {

    private static final HttpEntity DEFAULT_HTTP_ENTITY;

    private final Set<NsqdNode> nsqdNodes;
    private final NsqdNodeSelector selector;
    private final RestTemplate restTemplate = new RestTemplateBuilder()
            .errorHandler(new DefaultResponseErrorHandler() {
                @Override
                public void handleError(ClientHttpResponse response) {
                }
            })
            .build();

    static {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(MediaType.parseMediaTypes("application/json;charset=utf-8"));
        DEFAULT_HTTP_ENTITY = new HttpEntity(headers);
    }

    public DefaultNsqdClient(Set<NsqdNode> nsqdNodes, NsqdNodeSelector selector) {
        this.nsqdNodes = nsqdNodes;
        this.selector = selector;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean health() {
        final NsqdNode node = selector.select(nsqdNodes);

        final String url = UriComponentsBuilder.newInstance()
                .scheme(node.getProtocol().getValue())
                .host(node.getHost())
                .port(node.getPort())
                .path("/stats")
                .queryParam("format", "json")
                .toUriString();

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, DEFAULT_HTTP_ENTITY, Map.class);

        Map<Object, Object> body = response.getBody();
        if (body == null) {
            return false;
        }

        return response.getStatusCodeValue() == 200 && Objects.equals("OK", body.get("health"));
    }

    @Override
    public Info info() {
        final NsqdNode node = selector.select(nsqdNodes);

        final String url = UriComponentsBuilder.newInstance()
                .scheme(node.getProtocol().getValue())
                .host(node.getHost())
                .port(node.getPort())
                .path("/info")
                .toUriString();

        ResponseEntity<Info> response = restTemplate.exchange(url, HttpMethod.GET, DEFAULT_HTTP_ENTITY, Info.class);
        return response.getBody();
    }

    @Override
    public String ping() {
        final NsqdNode node = selector.select(nsqdNodes);

        final String url = UriComponentsBuilder.newInstance()
                .scheme(node.getProtocol().getValue())
                .host(node.getHost())
                .port(node.getPort())
                .path("/ping")
                .toUriString();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, DEFAULT_HTTP_ENTITY, String.class);
        return response.getBody();
    }

    @Override
    public void pub(String topic, String message, long defer) {
        final NsqdNode node = selector.select(nsqdNodes);

        val urlBuilder = UriComponentsBuilder.newInstance()
                .scheme(node.getProtocol().getValue())
                .host(node.getHost())
                .port(node.getPort())
                .path("/pub")
                .queryParam("topic", topic);

        if (defer > 0) {
            urlBuilder.queryParam("defer", defer);
        }

        val url = urlBuilder.toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(MediaType.parseMediaTypes("application/json;charset=utf-8"));
        HttpEntity<String> httpEntity = new HttpEntity<>(message, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        checkResponse(response);
    }

    @Override
    public void mpub(String topic, String message, boolean binary) {
        final NsqdNode node = selector.select(nsqdNodes);

        val url = UriComponentsBuilder.newInstance()
                .scheme(node.getProtocol().getValue())
                .host(node.getHost())
                .port(node.getPort())
                .path("/mpub")
                .queryParam("topic", topic)
                .queryParam("binary", binary)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(MediaType.parseMediaTypes("application/json;charset=utf-8"));
        HttpEntity<String> httpEntity = new HttpEntity<>(message, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        checkResponse(response);

    }

    @Override
    public void createTopic(String topic) {
        final NsqdNode node = selector.select(nsqdNodes);

        val url = UriComponentsBuilder.newInstance()
                .scheme(node.getProtocol().getValue())
                .host(node.getHost())
                .port(node.getPort())
                .path("/topic/create")
                .queryParam("topic", topic)
                .toUriString();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, DEFAULT_HTTP_ENTITY, String.class);
        checkResponse(response);
    }

    @Override
    public void deleteTopic(String topic) {

        final NsqdNode node = selector.select(nsqdNodes);

        val url = UriComponentsBuilder.newInstance()
                .scheme(node.getProtocol().getValue())
                .host(node.getHost())
                .port(node.getPort())
                .path("/topic/delete")
                .queryParam("topic", topic)
                .toUriString();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, DEFAULT_HTTP_ENTITY, String.class);
        checkResponse(response);
    }

    @Override
    public void createChannel(String topic, String channel) {
        final NsqdNode node = selector.select(nsqdNodes);

        val url = UriComponentsBuilder.newInstance()
                .scheme(node.getProtocol().getValue())
                .host(node.getHost())
                .port(node.getPort())
                .path("/channel/create")
                .queryParam("topic", topic)
                .queryParam("channel", channel)
                .toUriString();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, DEFAULT_HTTP_ENTITY, String.class);
        checkResponse(response);
    }

    @Override
    public void deleteChannel(String topic, String channel) {
        final NsqdNode node = selector.select(nsqdNodes);

        val url = UriComponentsBuilder.newInstance()
                .scheme(node.getProtocol().getValue())
                .host(node.getHost())
                .port(node.getPort())
                .path("/channel/delete")
                .queryParam("topic", topic)
                .queryParam("channel", channel)
                .toUriString();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, DEFAULT_HTTP_ENTITY, String.class);
        checkResponse(response);
    }

    @Override
    public void emptyTopic(String topic) {
        final NsqdNode node = selector.select(nsqdNodes);

        val url = UriComponentsBuilder.newInstance()
                .scheme(node.getProtocol().getValue())
                .host(node.getHost())
                .port(node.getPort())
                .path("/topic/empty")
                .queryParam("topic", topic)
                .toUriString();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, DEFAULT_HTTP_ENTITY, String.class);
        checkResponse(response);
    }

    @Override
    public void emptyChannel(String topic, String channel) {
        final NsqdNode node = selector.select(nsqdNodes);

        val url = UriComponentsBuilder.newInstance()
                .scheme(node.getProtocol().getValue())
                .host(node.getHost())
                .port(node.getPort())
                .path("/channel/empty")
                .queryParam("topic", topic)
                .queryParam("channel", channel)
                .toUriString();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, DEFAULT_HTTP_ENTITY, String.class);
        checkResponse(response);
    }

    @Override
    public void pauseTopic(String topic) {
        final NsqdNode node = selector.select(nsqdNodes);

        val url = UriComponentsBuilder.newInstance()
                .scheme(node.getProtocol().getValue())
                .host(node.getHost())
                .port(node.getPort())
                .path("/topic/pause")
                .queryParam("topic", topic)
                .toUriString();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, DEFAULT_HTTP_ENTITY, String.class);
        checkResponse(response);
    }

    @Override
    public void unpauseTopic(String topic) {
        final NsqdNode node = selector.select(nsqdNodes);

        val url = UriComponentsBuilder.newInstance()
                .scheme(node.getProtocol().getValue())
                .host(node.getHost())
                .port(node.getPort())
                .path("/topic/unpause")
                .queryParam("topic", topic)
                .toUriString();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, DEFAULT_HTTP_ENTITY, String.class);
        checkResponse(response);
    }

    @Override
    public void pauseChannel(String topic, String channel) {
        final NsqdNode node = selector.select(nsqdNodes);

        val url = UriComponentsBuilder.newInstance()
                .scheme(node.getProtocol().getValue())
                .host(node.getHost())
                .port(node.getPort())
                .path("/channel/pause")
                .queryParam("topic", topic)
                .queryParam("channel", channel)
                .toUriString();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, DEFAULT_HTTP_ENTITY, String.class);
        checkResponse(response);
    }

    @Override
    public void unpauseChannel(String topic, String channel) {
        final NsqdNode node = selector.select(nsqdNodes);

        val url = UriComponentsBuilder.newInstance()
                .scheme(node.getProtocol().getValue())
                .host(node.getHost())
                .port(node.getPort())
                .path("/channel/unpause")
                .queryParam("topic", topic)
                .queryParam("channel", channel)
                .toUriString();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, DEFAULT_HTTP_ENTITY, String.class);
        checkResponse(response);
    }

}
