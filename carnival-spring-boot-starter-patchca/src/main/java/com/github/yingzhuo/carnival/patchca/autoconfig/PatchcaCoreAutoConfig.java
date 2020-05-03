/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.patchca.autoconfig;

import com.github.yingzhuo.carnival.patchca.CaptchaDao;
import com.github.yingzhuo.carnival.patchca.CaptchaHandler;
import com.github.yingzhuo.carnival.patchca.CaptchaSessionAttribute;
import com.github.yingzhuo.carnival.patchca.SessionPatchca;
import com.github.yingzhuo.carnival.patchca.core.PatchcaCoreFilter;
import com.github.yingzhuo.carnival.patchca.props.PatchcaProps;
import lombok.val;
import org.patchca.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 应卓
 */
@Lazy(false)
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "carnival.patchca", name = "enabled", havingValue = "true", matchIfMissing = true)
@AutoConfigureAfter(PatchcaBeanAutoConfig.class)
public class PatchcaCoreAutoConfig implements WebMvcConfigurer {

    @Autowired
    private PatchcaProps props;

    @Bean
    @ConditionalOnProperty(prefix = "carnival.patchca.servlet-filter", name = "enabled", havingValue = "true", matchIfMissing = true)
    public FilterRegistrationBean<PatchcaCoreFilter> patchcaFilter(
            CaptchaDao dao,
            CaptchaHandler handler,
            CaptchaService service
    ) throws Exception {

        val servletFilterProps = props.getServletFilter();
        val filter = new PatchcaCoreFilter();
        filter.setCaptchaService(service);
        filter.setCaptchaDao(dao);
        filter.setCaptchaHandler(handler);
        filter.afterPropertiesSet();

        val bean = new FilterRegistrationBean<PatchcaCoreFilter>();
        bean.setFilter(filter);
        bean.addUrlPatterns(servletFilterProps.getUrlPatterns());
        bean.setName(PatchcaCoreFilter.class.getSimpleName());
        return bean;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PatchcaHandlerMethodArgumentResolver());
    }

    // -----------------------------------------------------------------------------------------------------------------

    private static class PatchcaHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver, CaptchaSessionAttribute {
        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            return parameter.hasParameterAnnotation(SessionPatchca.class);
        }

        @Override
        public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
            return ((HttpServletRequest) webRequest.getNativeRequest()).getSession(true).getAttribute(NAME);
        }
    }

}
