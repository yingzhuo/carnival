/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.autoconfig;

import com.github.yingzhuo.carnival.spring.ApplicationContextProvider;
import com.github.yingzhuo.carnival.spring.SpringUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

import java.util.UUID;

/**
 * @author 应卓
 * @since 1.0.0
 */
public class SpringUtilsAutoConfig implements Ordered {

    @Bean
    @ConditionalOnMissingBean
    public ApplicationContextProvider applicationContextProvider() {
        return ApplicationContextProvider.INSTANCE;
    }

    @Bean(name = SpringUtils.__identity__)
    public IdentityFactoryBean identity() {
        return new IdentityFactoryBean();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    // -----------------------------------------------------------------------------------------------------------------

    static final class IdentityFactoryBean implements FactoryBean<String> {

        @Override
        public String getObject() {
            return UUID.randomUUID().toString();
        }

        @Override
        public Class<?> getObjectType() {
            return String.class;
        }
    }

}
