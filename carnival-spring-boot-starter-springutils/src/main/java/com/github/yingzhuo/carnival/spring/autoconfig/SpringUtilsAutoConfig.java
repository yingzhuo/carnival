/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.spring.autoconfig;

import com.github.yingzhuo.carnival.spring.SpringUtils;
import com.github.yingzhuo.carnival.spring.SpringUtilsApplicationContextAware;
import com.github.yingzhuo.carnival.spring.tool.SpringIdFactoryBean;
import com.github.yingzhuo.carnival.spring.tool.WebApplicationAnchor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @see SpringUtils
 */
public class SpringUtilsAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public SpringUtilsApplicationContextAware springUtilsApplicationContextAware() {
        return SpringUtilsApplicationContextAware.INSTANCE;
    }

    @Bean
    @ConditionalOnWebApplication
    public WebApplicationAnchor webApplicationAnchor() {
        return WebApplicationAnchor.INSTANCE;
    }

    @Bean(name = "springId")
    @ConditionalOnMissingBean
    public SpringIdFactoryBean springId() {
        return new SpringIdFactoryBean();
    }

}
