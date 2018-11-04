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

import com.github.yingzhuo.carnival.common.mvc.interceptor.HandlerInterceptorSupport;
import com.github.yingzhuo.carnival.datasource.DataSourceSwitch;
import com.github.yingzhuo.carnival.datasource.ForkDataSource;
import com.github.yingzhuo.carnival.spring.SpringUtils;
import lombok.val;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
public class ForkDataSourceWebAutoConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ForkDataSourceSwitchInterceptor()).addPathPatterns("/", "/**").order(Ordered.HIGHEST_PRECEDENCE);
    }

    private static final class ForkDataSourceSwitchInterceptor extends HandlerInterceptorSupport {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            val annotationOption = getMethodAnnotation(DataSourceSwitch.class, handler);

            annotationOption.ifPresent(annotation -> {
                try {
                    ForkDataSource datasource = SpringUtils.getBean(ForkDataSource.class);
                    datasource.getRemote().setName(annotation.value());
                } catch (NoSuchBeanDefinitionException e) {
                    // NOP
                }
            });

            return true;
        }
    }

}
