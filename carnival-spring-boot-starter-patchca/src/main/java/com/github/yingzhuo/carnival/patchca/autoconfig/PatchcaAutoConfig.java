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

import com.github.yingzhuo.carnival.patchca.PatchcaServletFilter;
import com.github.yingzhuo.carnival.patchca.props.ServletFilterProps;
import com.github.yingzhuo.carnival.patchca.support.SessionPatchcaHandlerMethodArgumentResolver;
import org.patchca.background.BackgroundFactory;
import org.patchca.color.ColorFactory;
import org.patchca.filter.FilterFactory;
import org.patchca.font.FontFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.text.renderer.TextRenderer;
import org.patchca.word.WordFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author 应卓
 */
@ConditionalOnProperty(prefix = "carnival.patchca", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnWebApplication
@AutoConfigureAfter(PatchcaBeanAutoConfig.class)
public class PatchcaAutoConfig implements WebMvcConfigurer {

    private final ServletFilterProps servletFilterProps;

    public PatchcaAutoConfig(ServletFilterProps servletFilterProps) {
        this.servletFilterProps = servletFilterProps;
    }

    @Bean
    public FilterRegistrationBean<PatchcaServletFilter> patchcaFilter(
            WordFactory wordFactory,
            ServletFilterProps servletFilterProps,
            BackgroundFactory backgroundFactory,
            ColorFactory colorFactory,
            FontFactory fontFactory,
            FilterFactory filterFactory,
            TextRenderer textRenderer
    ) {
        ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
        cs.setBackgroundFactory(backgroundFactory);
        cs.setFontFactory(fontFactory);
        cs.setTextRenderer(textRenderer);
        cs.setColorFactory(colorFactory);
        cs.setWordFactory(wordFactory);
        cs.setWidth(servletFilterProps.getWidth());
        cs.setHeight(servletFilterProps.getHeight());
        cs.setFilterFactory(filterFactory);

        PatchcaServletFilter patchcaFilter = new PatchcaServletFilter();
        patchcaFilter.setCaptchaService(cs);
        patchcaFilter.setPatchcaSessionAttributeName(servletFilterProps.getSessionAttributeName());

        final FilterRegistrationBean<PatchcaServletFilter> bean = new FilterRegistrationBean<>();
        bean.setEnabled(true);
        bean.setFilter(patchcaFilter);
        bean.addUrlPatterns(servletFilterProps.getUrlPatterns());
        bean.setName(servletFilterProps.getName());
        bean.setOrder(servletFilterProps.getOrder());

        return bean;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SessionPatchcaHandlerMethodArgumentResolver(servletFilterProps.getSessionAttributeName()));
    }

}
