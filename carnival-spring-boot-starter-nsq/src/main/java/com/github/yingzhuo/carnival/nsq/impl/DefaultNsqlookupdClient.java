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

import com.github.yingzhuo.carnival.nsq.NsqlookupdClient;
import com.github.yingzhuo.carnival.nsq.model.NodeResult;
import com.github.yingzhuo.carnival.nsq.node.NsqlookupdNode;
import com.github.yingzhuo.carnival.nsq.selector.NsqlookupdNodeSelector;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

/**
 * @author 应卓
 * @since 1.3.1
 */
public class DefaultNsqlookupdClient implements NsqlookupdClient {

    private static final HttpEntity DEFAULT_HTTP_ENTITY;

//    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

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

    private Set<NsqlookupdNode> nodes;
    private NsqlookupdNodeSelector selector;

    public DefaultNsqlookupdClient(Set<NsqlookupdNode> nodes, NsqlookupdNodeSelector selector) {
        this.nodes = nodes;
        this.selector = selector;
    }

    @Override
    public NodeResult nodes() {
        final NsqlookupdNode node = selector.select(nodes);

        final String url = UriComponentsBuilder.newInstance()
                .scheme(node.getProtocol().getValue())
                .host(node.getHost())
                .port(node.getPort())
                .path("/nodes")
                .toUriString();

        ResponseEntity<NodeResult> response = restTemplate.exchange(url, HttpMethod.GET, DEFAULT_HTTP_ENTITY, NodeResult.class);
        return response.getBody();
    }

}
