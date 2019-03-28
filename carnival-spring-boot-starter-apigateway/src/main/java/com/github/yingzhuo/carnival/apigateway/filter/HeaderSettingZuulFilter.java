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

import java.util.Map;

/**
 * @author 应卓
 */
public class HeaderSettingZuulFilter extends AbstractZuulFilter {

    private Map<String, String> headers;

    public HeaderSettingZuulFilter(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    protected void doRun(RequestContext requestContext) {
        for (String name : headers.keySet()) {
            String value = headers.get(name);
            requestContext.addZuulRequestHeader(name, value);
        }
    }

    @Override
    protected boolean doShouldFilter(RequestContext requestContext) {
        return headers != null && !headers.isEmpty();
    }

}
