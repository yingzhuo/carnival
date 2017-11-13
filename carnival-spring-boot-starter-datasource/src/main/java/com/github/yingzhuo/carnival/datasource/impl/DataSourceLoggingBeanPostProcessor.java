/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.datasource.impl;

import com.github.yingzhuo.carnival.datasource.DataSourceWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.sql.DataSource;

@Slf4j
public class DataSourceLoggingBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof DataSourceWrapper) {
            doLog((DataSourceWrapper) bean, beanName);
            return bean;
        }

        if (bean instanceof DataSource) {
            doLog((DataSource) bean, beanName);
            return bean;
        }

        return bean;
    }

    private void doLog(DataSourceWrapper dataSource, String beanName) {
        log.info("DataSourceWrapper(beanName={}) contains: ", beanName);
        dataSource.getDataSourceNameSet().forEach(log::info);
    }

    private void doLog(DataSource dataSource, String beanName) {
        log.info("DataSource(beanName={})", beanName);
    }

}
