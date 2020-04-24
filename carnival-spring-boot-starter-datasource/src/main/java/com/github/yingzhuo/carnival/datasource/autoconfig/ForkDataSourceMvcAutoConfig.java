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

import com.github.yingzhuo.carnival.datasource.fork.ForkDataSource;
import com.github.yingzhuo.carnival.datasource.fork.ForkDataSourceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 应卓
 * @since 1.3.7
 */
@Lazy(false)
@ConditionalOnWebApplication
public class ForkDataSourceMvcAutoConfig implements WebMvcConfigurer {

    @Autowired(required = false)
    private ForkDataSource forkDataSource;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (forkDataSource != null) {
            registry.addInterceptor(new ForkDataSourceInterceptor(forkDataSource)).addPathPatterns("/", "/**");
        }
    }

}
