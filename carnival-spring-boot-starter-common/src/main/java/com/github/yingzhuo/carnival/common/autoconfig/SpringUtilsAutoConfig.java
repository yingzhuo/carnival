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

import com.github.yingzhuo.carnival.spring.SpringUtilsInitBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

/**
 * @author 应卓
 */
public class SpringUtilsAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public SpringUtilsInitBean springUtilsInitBean() {
        return SpringUtilsInitBean.INSTANCE;
    }

    @Bean(name = "__identity__")
    public IdentityFactoryBean identity() {
        return new IdentityFactoryBean();
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
