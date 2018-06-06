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
import com.netflix.zuul.exception.ZuulException;
import org.springframework.core.Ordered;

/**
 * @author 应卓
 */
public class ContentTypeHttpHeaderSetterFilter extends AbstractZuulFilter {

    private final static String CONTENT_TYPE = "Content-Type";
    private final static String APPLICATION_JSON = "application/json;charset=UTF-8";

    public ContentTypeHttpHeaderSetterFilter() {
        super.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        super.setOrder(Integer.MIN_VALUE);
        super.setFilterType(FilterType.PRE);
    }

    @Override
    protected boolean doShouldFilter(RequestContext requestContext) {
        // 除了GET请求外，其他请求都设置请求头
        return !"get".equalsIgnoreCase(requestContext.getRequest().getMethod());
    }

    @Override
    protected void doRun(RequestContext requestContext) throws ZuulException {
        requestContext.addZuulRequestHeader(CONTENT_TYPE, APPLICATION_JSON);
    }

}
