/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.actuator.health;

import lombok.val;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;

/**
 * @author 应卓
 * @since 1.6.14
 */
public abstract class AbstractHttpHealthIndicator extends AbstractHealthIndicator implements InitializingBean {

    private RestTemplate restTemplate;
    private String target;
    private HttpMethod method = HttpMethod.GET;
    private Duration timeout = Duration.ofSeconds(2L);
    private String httpBasicUsername;
    private String httpBasicPassword;

    public AbstractHttpHealthIndicator() {
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        try {
            val response = restTemplate.exchange(target, method, createHttpEntity(), String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                builder.up();
            } else {
                builder.down();
            }
        } catch (RestClientException e) {
            builder.down();
        }
    }

    private HttpEntity<?> createHttpEntity() {
        if (httpBasicUsername == null || httpBasicPassword == null) {
            return HttpEntity.EMPTY;
        }

        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION,
                "Basic " + Base64.getUrlEncoder().encodeToString((httpBasicUsername + ":" + httpBasicPassword).getBytes(StandardCharsets.UTF_8))
        );

        return new HttpEntity<>(headers);
    }

    @Override
    public void afterPropertiesSet() {
        Assert.hasText(target, "target is null or empty");

        this.restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(timeout)
                .setReadTimeout(timeout)
                .build();
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public void setHttpBasicUsername(String httpBasicUsername) {
        this.httpBasicUsername = httpBasicUsername;
    }

    public void setHttpBasicPassword(String httpBasicPassword) {
        this.httpBasicPassword = httpBasicPassword;
    }

}
