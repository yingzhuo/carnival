/*
**      _          _ _
**  ___| | ___   _| (_)_ __   ___
** / __| |/ / | | | | | '_ \ / _ \
** \__ \   <| |_| | | | | | |  __/
** |___/_|\_\\__, |_|_|_| |_|\___|
**           |___/
*/

package com.github.yingzhuo.carnival.patchca.autoconfig;

import com.github.yingzhuo.carnival.patchca.*;
import com.github.yingzhuo.carnival.patchca.props.*;
import lombok.extern.slf4j.Slf4j;
import org.patchca.background.SingleColorBackgroundFactory;
import org.patchca.color.ColorFactory;
import org.patchca.filter.FilterFactory;
import org.patchca.filter.predefined.*;
import org.patchca.font.RandomFontFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.text.renderer.BestFitTextRenderer;
import org.patchca.word.AdaptiveRandomWordFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@EnableConfigurationProperties({
        SwitchProps.class,
        PatchcaFilterProps.class,
        BackgroundProps.class,
        ForegroundProps.class,
        FontProps.class,
        WordProps.class,
        TextRendererProps.class
})
@ConditionalOnProperty(prefix = "carnival.patchca", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnWebApplication
public class PatchcaConfiguration extends WebMvcConfigurerAdapter {

    private final PatchcaFilterProps filterProps;

    public PatchcaConfiguration(PatchcaFilterProps filterProps) {
        this.filterProps = filterProps;
    }

    @PostConstruct
    private void init() {
        log.debug("SpringBoot auto-config: {}", getClass().getName());
    }

    @Bean
    public FilterRegistrationBean patchcaFilter(
            PatchcaFilterProps filterProps,
            BackgroundProps backgroundProps,
            ForegroundProps foregroundProps,
            FontProps fontProps,
            WordProps wordProps,
            TextRendererProps textRendererProps
    ) {
        // 文本内容
        AdaptiveRandomWordFactory wordFactory = new AdaptiveRandomWordFactory();
        wordFactory.setWideCharacters(wordProps.getWideCharacters());
        wordFactory.setCharacters(wordProps.getCharacters());
        wordFactory.setMaxLength(wordProps.getMaxLength());
        wordFactory.setMinLength(wordProps.getMinLength());

        // 字体
        RandomFontFactory fontFactory = new RandomFontFactory();
        fontFactory.setFamilies(Arrays.asList(fontProps.getFamilies()));
        fontFactory.setMaxSize(fontProps.getMaxSize());
        fontFactory.setMinSize(fontProps.getMinSize());

        // 效果
        BestFitTextRenderer textRenderer = new BestFitTextRenderer();
        textRenderer.setBottomMargin(textRendererProps.getBottomMargin());
        textRenderer.setTopMargin(textRendererProps.getTopMargin());
        textRenderer.setLeftMargin(textRendererProps.getLeftMargin());
        textRenderer.setRightMargin(textRendererProps.getRightMargin());

        // 背景
        SingleColorBackgroundFactory backgroundFactory = new SingleColorBackgroundFactory();
        backgroundFactory.setColorFactory(backgroundProps.createColorFactory());

        // 字体
        ColorFactory colorFactory = foregroundProps.createColorFactory();

        ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
        cs.setBackgroundFactory(backgroundFactory);
        cs.setFontFactory(fontFactory);
        cs.setTextRenderer(textRenderer);
        cs.setColorFactory(colorFactory);
        cs.setWordFactory(wordFactory);
        cs.setWidth(filterProps.getWidth());
        cs.setHeight(filterProps.getHeight());

        // 滤镜
        FilterFactory filterFactory;
        switch (filterProps.getFilterType()) {
            case CURVES:
                filterFactory = new CurvesAbstractRippleFilterFactory(colorFactory);
                break;
            case DIFFUSE:
                filterFactory = new DiffuseAbstractRippleFilterFactory();
                break;
            case DOUBLE:
                filterFactory = new DoubleRippleFilterFactory();
                break;
            case MARBLE:
                filterFactory = new MarbleAbstractRippleFilterFactory();
                break;
            case WOBBLE:
                filterFactory = new WobbleAbstractRippleFilterFactory();
                break;
            default:
                throw new AssertionError(); // 代码不可能运行到此处
        }
        cs.setFilterFactory(filterFactory);

        PatchcaFilter patchcaFilter = new PatchcaFilter();
        patchcaFilter.setCaptchaService(cs);
        patchcaFilter.setPatchcaSessionAttributeName(filterProps.getSessionAttributeName());

        FilterRegistrationBean bean = new FilterRegistrationBean();
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

    private static class SessionPatchcaHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
        private final String sessionAttributeName;

        public SessionPatchcaHandlerMethodArgumentResolver(String sessionAttributeName) {
            this.sessionAttributeName = sessionAttributeName;
        }

        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            boolean c1 = parameter.hasParameterAnnotation(SessionPatchca.class) && parameter.getParameterType() == String.class;
            boolean c2 = parameter.hasParameterAnnotation(SessionPatchca.class) && parameter.getParameterType() == Optional.class;
            return c1 || c2;
        }

        @Override
        public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
            HttpSession session = webRequest.getNativeRequest(HttpServletRequest.class).getSession();
            String patchca = (String) session.getAttribute(this.sessionAttributeName);

            boolean c1 = parameter.hasParameterAnnotation(SessionPatchca.class) && parameter.getParameterType() == String.class;
            if (c1) {
                return patchca;
            }

            boolean c2 = parameter.hasParameterAnnotation(SessionPatchca.class) && parameter.getParameterType() == Optional.class;
            if (c2) {
                return Optional.ofNullable(patchca);
            }

            return null;
        }
    }
}
