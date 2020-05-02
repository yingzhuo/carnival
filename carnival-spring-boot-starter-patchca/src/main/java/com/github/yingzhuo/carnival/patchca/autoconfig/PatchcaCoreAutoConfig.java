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
import org.patchca.background.BackgroundFactory;
import org.patchca.color.ColorFactory;
import org.patchca.filter.FilterFactory;
import org.patchca.font.FontFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.size.SizeFactory;
import org.patchca.text.renderer.TextRenderer;
import org.patchca.word.WordFactory;
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
    public FilterRegistrationBean<PatchcaCoreFilter> patchcaFilter(
            CaptchaDao captchaDao,
            CaptchaHandler captchaHandler,
            BackgroundFactory backgroundFactory,
            ColorFactory colorFactory,
            FontFactory fontFactory,
            TextRenderer textRenderer,
            FilterFactory filterFactory,
            WordFactory wordFactory,
            SizeFactory sizeFactory
    ) throws Exception {

        // service
        val svc = new ConfigurableCaptchaService();
        svc.setBackgroundFactory(backgroundFactory);
        svc.setFontFactory(fontFactory);
        svc.setTextRenderer(textRenderer);
        svc.setColorFactory(colorFactory);
        svc.setWordFactory(wordFactory);
        svc.setWidth(sizeFactory.getWidth());
        svc.setHeight(sizeFactory.getHeight());
        svc.setFilterFactory(filterFactory);

        // servlet-filter
        val servletFilterProps = props.getServletFilter();
        val coreFilter = new PatchcaCoreFilter();
        coreFilter.setCaptchaService(svc);
        coreFilter.setCaptchaDao(captchaDao);
        coreFilter.setCaptchaHandler(captchaHandler);
        coreFilter.afterPropertiesSet();

        val bean = new FilterRegistrationBean<PatchcaCoreFilter>();
        bean.setFilter(coreFilter);
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
