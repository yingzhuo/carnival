/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.autoconfig;

import com.github.yingzhuo.carnival.mvc.responsibility.Responsibility;
import com.github.yingzhuo.carnival.mvc.responsibility.ResponsibilityChain;
import com.github.yingzhuo.carnival.mvc.responsibility.ResponsibilityChainFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;

import java.util.Collections;
import java.util.List;

/**
 * @author 应卓
 * @since 1.6.14
 */
@Slf4j
@Lazy(false)
@ConditionalOnWebApplication
public class MvcResponsibilityAutoConfig {

    @Autowired(required = false)
    private List<Responsibility> responsibilities = Collections.emptyList();

    @Bean
    public FilterRegistrationBean<ResponsibilityChainFilter> responsibilityChainFilterFilter() {
        final FilterRegistrationBean<ResponsibilityChainFilter> bean = new FilterRegistrationBean<>(new ResponsibilityChainFilter(ResponsibilityChain.of(responsibilities)));
        bean.setName(ResponsibilityChainFilter.class.getSimpleName());
        bean.addUrlPatterns("/*");
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE + 100);
        return bean;
    }

}
