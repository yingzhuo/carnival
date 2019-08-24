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

/**
 * @author 应卓
 * @since 1.1.4
 */
public class BearerHttpZuulFilter extends AbstractZuulFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private final String value;

    public BearerHttpZuulFilter(String value) {
        this.value = value;
    }

    @Override
    protected void doRun(RequestContext requestContext) {
        requestContext.addZuulRequestHeader(AUTHORIZATION, BEARER + value);
    }

    public String getValue() {
        return value;
    }

}
