/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.apigateway.filter;

import com.netflix.zuul.context.RequestContext;
import lombok.val;
import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;

/**
 * @author 应卓
 */
public class HttpBasicHttpHeaderSetterZuulFilter extends AbstractZuulFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic ";

    private final String username;
    private final String password;

    public HttpBasicHttpHeaderSetterZuulFilter(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected void doRun(RequestContext requestContext) {
        val value = username + ":" +password;
        requestContext.addZuulRequestHeader(AUTHORIZATION, BASIC + Base64.encodeBase64URLSafeString(value.getBytes(StandardCharsets.UTF_8)));
    }

}
