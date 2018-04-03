/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.refuse.autoconfig;

import com.github.yingzhuo.carnival.refuse.RefusingConfigLoader;
import com.github.yingzhuo.carnival.refuse.RefusingListener;
import com.github.yingzhuo.carnival.refuse.mvc.RefusingFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
@AutoConfigureAfter(RefusingBeanConfiguration.class)
@Slf4j
public class RefusingCoreConfiguration extends WebMvcConfigurerAdapter {

    @PostConstruct
    private void init() {
        log.debug("SpringBoot auto-config: {}", getClass().getName());
    }

    @Bean
    public FilterRegistrationBean<RefusingFilter> refusingFilterRegistrationBean(RefusingConfigLoader loader, RefusingListener listener) {
        final FilterRegistrationBean<RefusingFilter> bean = new FilterRegistrationBean<>();
        bean.addUrlPatterns("/*");
        bean.setFilter(new RefusingFilter(loader, listener));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        bean.setName(RefusingFilter.class.getName());
        return bean;
    }

}
