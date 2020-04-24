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

import com.github.yingzhuo.carnival.patchca.props.*;
import lombok.val;
import org.patchca.background.BackgroundFactory;
import org.patchca.background.SingleColorBackgroundFactory;
import org.patchca.color.ColorFactory;
import org.patchca.filter.FilterFactory;
import org.patchca.filter.predefined.*;
import org.patchca.font.FontFactory;
import org.patchca.font.RandomFontFactory;
import org.patchca.text.renderer.BestFitTextRenderer;
import org.patchca.text.renderer.TextRenderer;
import org.patchca.word.AdaptiveRandomWordFactory;
import org.patchca.word.WordFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import java.util.Arrays;

/**
 * @author 应卓
 */
@Lazy(false)
@ConditionalOnProperty(prefix = "carnival.patchca", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({
        FilterProps.class,
        ServletFilterProps.class,
        BackgroundProps.class,
        ForegroundProps.class,
        FontProps.class,
        WordProps.class,
        TextRendererProps.class
})
@ConditionalOnWebApplication
public class PatchcaBeanAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public WordFactory wordFactory(WordProps props) {
        val bean = new AdaptiveRandomWordFactory();
        bean.setWideCharacters(props.getWideCharacters());
        bean.setCharacters(props.getCharacters());
        bean.setMaxLength(props.getMaxLength());
        bean.setMinLength(props.getMinLength());
        return bean;
    }

    @Bean
    @ConditionalOnMissingBean
    public BackgroundFactory backgroundFactory(BackgroundProps props) {
        val bean = new SingleColorBackgroundFactory();
        bean.setColorFactory(props.createColorFactory());
        return bean;
    }

    @Bean
    @ConditionalOnMissingBean
    public FontFactory fontFactory(FontProps props) {
        val bean = new RandomFontFactory();
        bean.setFamilies(Arrays.asList(props.getFamilies()));
        bean.setMaxSize(props.getMaxSize());
        bean.setMinSize(props.getMinSize());
        return bean;
    }

    @Bean
    @ConditionalOnMissingBean
    public TextRenderer textRenderer(TextRendererProps props) {
        val bean = new BestFitTextRenderer();
        bean.setBottomMargin(props.getBottomMargin());
        bean.setTopMargin(props.getTopMargin());
        bean.setLeftMargin(props.getLeftMargin());
        bean.setRightMargin(props.getRightMargin());
        return bean;
    }

    @Bean
    @ConditionalOnMissingBean
    public ColorFactory colorFactory(ForegroundProps props) {
        return props.createColorFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    public FilterFactory filterFactory(FilterProps filterProps, ColorFactory colorFactory) {
        FilterFactory filterFactory;
        switch (filterProps.getType()) {
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
        return filterFactory;
    }

}
