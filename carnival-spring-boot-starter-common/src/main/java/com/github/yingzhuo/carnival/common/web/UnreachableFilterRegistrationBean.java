/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.web;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.Ordered;

/**
 * @author 应卓
 */
public class UnreachableFilterRegistrationBean extends FilterRegistrationBean<javax.servlet.Filter> {

    public UnreachableFilterRegistrationBean() {
        super.setFilter(new NopFilter(false));
        super.setOrder(Ordered.LOWEST_PRECEDENCE);
        super.addUrlPatterns("/" + java.util.UUID.randomUUID().toString());
        super.setName("UntouchableFilter");
    }

}
