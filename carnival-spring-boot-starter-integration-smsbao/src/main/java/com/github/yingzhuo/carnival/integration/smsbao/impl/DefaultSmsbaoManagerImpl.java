/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.integration.smsbao.impl;

import com.github.yingzhuo.carnival.integration.smsbao.SmsbaoManager;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * @author 应卓
 */
public class DefaultSmsbaoManagerImpl implements SmsbaoManager {

    private RestTemplate restTemplate;
    private String username;
    private String password;

    public DefaultSmsbaoManagerImpl(RestTemplate restTemplate, String username, String password) {
        this.restTemplate = restTemplate;
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean send(String phoneNumber, String content) {
        final String url = "http://api.smsbao.com/sms";
        String parameters = "u=" + getUsername() + "&p=" + DigestUtils.md5DigestAsHex(getPassword().getBytes(StandardCharsets.UTF_8)) + "&m=" + phoneNumber + "&c=" + content;
        String body = sendPostRequest(url, parameters);
        return "0".equals(body);
    }

    private String sendPostRequest(String url, String parameters) {
        final String finalUrl = url + "?" + parameters;
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(finalUrl, null, String.class);
        return responseEntity.getBody();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
