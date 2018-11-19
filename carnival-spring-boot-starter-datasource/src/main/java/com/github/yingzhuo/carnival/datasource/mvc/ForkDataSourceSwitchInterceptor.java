/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.datasource.mvc;

import com.github.yingzhuo.carnival.common.mvc.interceptor.HandlerInterceptorSupport;
import com.github.yingzhuo.carnival.datasource.DataSourceSwitch;
import com.github.yingzhuo.carnival.datasource.ForkDataSource;
import com.github.yingzhuo.carnival.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 应卓
 */
@Slf4j
public class ForkDataSourceSwitchInterceptor extends HandlerInterceptorSupport {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        val annotationOption = getMethodAnnotation(DataSourceSwitch.class, handler);

        annotationOption.ifPresent(annotation -> {
            try {
                val dataSourceName = annotation.value();
                ForkDataSource datasource = SpringUtils.getBean(ForkDataSource.class);
                datasource.getRemote().setName(dataSourceName);
                log.info("DataSource switch -> ({})", dataSourceName);
            } catch (NoSuchBeanDefinitionException e) {
                // NOP
            }
        });

        return true;
    }

}
