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
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

/**
 * @author 应卓
 * @see SpringUtils
 */
public class SpringUtilsAutoConfig {

    @Bean
    public SpringUtilsApplicationContextAware springUtilsApplicationContextAware() {
        return SpringUtilsApplicationContextAware.INSTANCE;
    }

    @Bean
    @ConditionalOnWebApplication
    public WebApplicationAnchor webApplicationAnchor() {
        return new WebApplicationAnchor();
    }

    @Bean(name = "springId")
    @ConditionalOnMissingBean
    public SpringIdFactoryBean springId() {
        return new SpringIdFactoryBean();
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    final public class WebApplicationAnchor {
    }

    /*----------------------------------------------------------------------------------------------------------------*/
    final public class SpringIdFactoryBean implements InitializingBean, FactoryBean<String> {

        private String uuid;

        @Override
        public void afterPropertiesSet() throws Exception {
            this.uuid = UUID.randomUUID().toString();
        }

        @Override
        public String getObject() throws Exception {
            return this.uuid;
        }

        @Override
        public Class<?> getObjectType() {
            return String.class;
        }
    }

}
