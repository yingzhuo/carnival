/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.nsq.lookup;

import com.github.yingzhuo.carnival.nsq.config.LookupConfig;
import com.github.yingzhuo.carnival.nsq.node.NsqdNode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 应卓
 * @since 1.3.1
 */
public class FinderImpl implements Finder {

    private List<LookupConfig> nsqLookupdNodeList;

    private final RestTemplate restTemplate = new RestTemplateBuilder()
            .errorHandler(new DefaultResponseErrorHandler() {
                @Override
                public void handleError(URI url, HttpMethod method, ClientHttpResponse response) {
                }
            })
            .build();

    @Override
    public List<NsqdNode> find(String topic) {

        Set<NsqdNode> set = new HashSet<>();
        for (LookupConfig it : nsqLookupdNodeList) {
            set.addAll(doFind(it, topic));
        }

        return new ArrayList<>(set);
    }

    protected Set<NsqdNode> doFind(LookupConfig cnf, String topic) {

        final String url = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(cnf.getHost())
                .port(cnf.getHttpPort())
                .path("/lookup")
                .queryParam("topic", topic)
                .toUriString();

        ResponseEntity<ProducerWrapper> responseEntity = restTemplate.getForEntity(url, ProducerWrapper.class);

        if (responseEntity.getStatusCodeValue() == 404) {
            return new HashSet<>();
        } else {

            final ProducerWrapper body = responseEntity.getBody();
            return body == null ? new HashSet<>() :new HashSet<>(responseEntity.getBody().getProducers());
        }
    }

    public void setNsqLookupdNodeList(List<LookupConfig> nsqLookupdNodeList) {
        this.nsqLookupdNodeList = nsqLookupdNodeList;
    }

    @Getter
    @Setter
    private static class ProducerWrapper {
        private List<NsqdNode> producers;
    }

}
