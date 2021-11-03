/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.autoconfig;

import com.github.yingzhuo.carnival.mvc.support.ImageHandlerMethodReturnValueHandler;
import com.github.yingzhuo.carnival.mvc.support.IpAddressHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.Ordered;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.BeanNameViewResolver;

import java.util.List;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
class MvcCommonAutoConfig implements WebMvcConfigurer {

    @Autowired(required = false)
    public void config(BeanNameViewResolver resolver) {
        if (resolver != null) {
            resolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
        }
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new IpAddressHandlerMethodArgumentResolver());
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        handlers.add(new ImageHandlerMethodReturnValueHandler());
    }

}
