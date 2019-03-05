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

import com.github.yingzhuo.carnival.common.Null;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @author 应卓
 */
public abstract class AbstractZuulFilter extends ZuulFilter {

    private int order = 0;
    private FilterType filterType = FilterType.PRE;

    @Override
    public final String filterType() {
        return this.filterType.getValue();
    }

    @Override
    public final int filterOrder() {
        return this.order;
    }

    @Override
    public final boolean shouldFilter() {
        return doShouldFilter(RequestContext.getCurrentContext());
    }

    @Override
    public final Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        doRun(ctx);
        return Null.INSTANCE;
    }

    protected boolean doShouldFilter(RequestContext requestContext) {
        return true;
    }

    protected abstract void doRun(RequestContext requestContext) throws ZuulException;

    public void setOrder(int order) {
        this.order = order;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    // ----------------------------------------------------------------------------------------------------------------

    public enum FilterType {

        PRE("pre"), POST("post");   // 暂时不支持其他类型

        private final String value;

        FilterType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
