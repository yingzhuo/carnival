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

import com.github.yingzhuo.carnival.patchca.PatchcaFilter;
import com.github.yingzhuo.carnival.patchca.props.PatchcaFilterProps;
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

    private final PatchcaFilterProps filterProps;

    public PatchcaAutoConfig(PatchcaFilterProps filterProps) {
        this.filterProps = filterProps;
    }

    @Bean
    public FilterRegistrationBean<PatchcaFilter> patchcaFilter(
            PatchcaFilterProps filterProps,
            WordFactory wordFactory,
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
        cs.setWidth(filterProps.getWidth());
        cs.setHeight(filterProps.getHeight());
        cs.setFilterFactory(filterFactory);

        PatchcaFilter patchcaFilter = new PatchcaFilter();
        patchcaFilter.setCaptchaService(cs);
        patchcaFilter.setPatchcaSessionAttributeName(filterProps.getSessionAttributeName());

        final FilterRegistrationBean<PatchcaFilter> bean = new FilterRegistrationBean<>();
        bean.setEnabled(true);
        bean.setFilter(patchcaFilter);
        bean.addUrlPatterns(filterProps.getUrlPatterns());
        bean.setName(filterProps.getName());
        bean.setOrder(filterProps.getOrder());

        return bean;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SessionPatchcaHandlerMethodArgumentResolver(filterProps.getSessionAttributeName()));
    }

}
