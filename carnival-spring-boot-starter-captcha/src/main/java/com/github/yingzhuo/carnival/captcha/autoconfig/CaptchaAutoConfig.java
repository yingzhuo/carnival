/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.captcha.autoconfig;

import com.github.yingzhuo.carnival.captcha.CaptchaDao;
import com.github.yingzhuo.carnival.captcha.CaptchaHandler;
import com.github.yingzhuo.carnival.captcha.CaptchaService;
import com.github.yingzhuo.carnival.captcha.core.CaptchaFilter;
import com.github.yingzhuo.carnival.captcha.dao.HttpSessionCaptchaDao;
import com.github.yingzhuo.carnival.captcha.handler.DefaultStatefulCaptchaHandler;
import com.github.yingzhuo.carnival.captcha.service.google.GoogleCaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author 应卓
 * @since 1.10.6
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
class CaptchaAutoConfig implements WebMvcConfigurer {

    @Autowired(required = false)
    private CaptchaDao dao = new HttpSessionCaptchaDao();

    @Autowired(required = false)
    private CaptchaHandler handler = new DefaultStatefulCaptchaHandler();

    @Autowired(required = false)
    private CaptchaService service = new GoogleCaptchaService();

    @Bean
    public FilterRegistrationBean<CaptchaFilter> patchcaFilter() {
        final CaptchaFilter filter = new CaptchaFilter();
        filter.setCaptchaDao(dao);
        filter.setCaptchaHandler(handler);
        filter.setCaptchaService(service);

        final FilterRegistrationBean<CaptchaFilter> bean = new FilterRegistrationBean<>(filter);
        bean.setName(CaptchaFilter.class.getName());
        bean.setOrder(Ordered.LOWEST_PRECEDENCE);
        bean.addUrlPatterns("/captcha"); // TODO:
        return bean;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // TODO: 支持元注释
    }

}
