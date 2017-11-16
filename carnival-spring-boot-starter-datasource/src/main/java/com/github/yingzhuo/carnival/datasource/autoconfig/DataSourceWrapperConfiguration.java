/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.datasource.autoconfig;

import com.github.yingzhuo.carnival.datasource.impl.DataSourceLoggingBeanPostProcessor;
import com.github.yingzhuo.carnival.datasource.impl.DataSourceWrapperAopAdvice;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@EnableConfigurationProperties(DataSourceWrapperConfiguration.DataSourceWrapperProps.class)
@ConditionalOnProperty(prefix = "carnival.datasource.wrapper", name = "enabled", havingValue = "true", matchIfMissing = true)
@Slf4j
public class DataSourceWrapperConfiguration {

    @PostConstruct
    private void init() {
        log.debug("SpringBoot auto-config: {}", getClass().getName());
    }

    @Bean
    @ConditionalOnMissingBean
    public DataSourceWrapperAopAdvice dataSourceWrapperAopAdvice(DataSourceWrapperProps props) {
        DataSourceWrapperAopAdvice advice = new DataSourceWrapperAopAdvice();
        advice.setOrder(props.getAdviceOrder());
        return advice;
    }

    @Bean
    public BeanPostProcessor dataSourceLoggingBeanPostProcessor() {
        return new DataSourceLoggingBeanPostProcessor();
    }

    @Data
    @ConfigurationProperties("carnival.datasource.wrapper")
    static class DataSourceWrapperProps {
        private boolean enabled = true;
        private int adviceOrder = 0;
    }

}
