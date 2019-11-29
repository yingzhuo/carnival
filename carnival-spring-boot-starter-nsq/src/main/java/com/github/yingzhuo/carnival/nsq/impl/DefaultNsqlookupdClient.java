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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yingzhuo.carnival.nsq.NsqlookupdClient;
import com.github.yingzhuo.carnival.nsq.model.NodeResult;
import com.github.yingzhuo.carnival.nsq.model.Producer;
import com.github.yingzhuo.carnival.nsq.node.NsqlookupdNode;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 应卓
 * @since 1.3.1
 */
@Slf4j
public class DefaultNsqlookupdClient implements NsqlookupdClient {

    private static final HttpEntity DEFAULT_HTTP_ENTITY;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

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

    public DefaultNsqlookupdClient(Set<NsqlookupdNode> nodes) {
        this.nodes = nodes;
    }

    @Override
    public List<Producer> lookup(String topic) {

        Set<Producer> set = new HashSet<>();        // 去除重复
        for (val node : this.nodes) {
            set.addAll(doLookup(topic, node));
        }

        return new ArrayList<>(set);
    }

    private Set<Producer> doLookup(String topic, NsqlookupdNode node) {

        final String url = UriComponentsBuilder.newInstance()
                .scheme(node.getProtocol().getValue())
                .host(node.getHost())
                .port(node.getPort())
                .path("/lookup")
                .toUriString();

        try {
            ResponseEntity<NodeResult> response = restTemplate.exchange(url, HttpMethod.GET, DEFAULT_HTTP_ENTITY, NodeResult.class);

            int code = response.getStatusCodeValue();
            if (code < 200 || code >= 300) {
                return new HashSet<>();
            }

            NodeResult nodeResult = response.getBody();

            if (nodeResult == null) {
                return new HashSet<>();
            } else {
                return new HashSet<>(nodeResult.getProducers());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new HashSet<>();     // lookupd连接不上
        }
    }

}
