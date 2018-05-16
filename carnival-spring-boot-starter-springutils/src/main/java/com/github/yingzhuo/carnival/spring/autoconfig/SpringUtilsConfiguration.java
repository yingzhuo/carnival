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
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @see SpringUtils
 */
public class SpringUtilsConfiguration {

    @Bean
    public SpringUtilsApplicationContextAware springUtilsApplicationContextAware() {
        return SpringUtilsApplicationContextAware.INSTANCE;
    }

    @Bean
    @ConditionalOnWebApplication
    public WebApplicationAnchor webApplicationAnchor() {
        return new WebApplicationAnchor();
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    final public class WebApplicationAnchor {
    }

}
