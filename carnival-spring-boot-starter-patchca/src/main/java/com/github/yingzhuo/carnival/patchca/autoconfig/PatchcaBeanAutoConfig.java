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
import com.github.yingzhuo.carnival.patchca.dao.HttpSessionCaptchaDao;
import com.github.yingzhuo.carnival.patchca.dao.MapCaptchaDao;
import com.github.yingzhuo.carnival.patchca.handler.DefaultStatefulCaptchaHandler;
import com.github.yingzhuo.carnival.patchca.handler.DefaultStatelessCaptchaHandler;
import com.github.yingzhuo.carnival.patchca.props.Mode;
import com.github.yingzhuo.carnival.patchca.props.PatchcaProps;
import lombok.val;
import org.patchca.background.BackgroundFactory;
import org.patchca.background.SingleColorBackgroundFactory;
import org.patchca.color.ColorFactory;
import org.patchca.filter.CompositeFilterFactory;
import org.patchca.filter.FilterFactory;
import org.patchca.filter.FilterType;
import org.patchca.filter.predefined.*;
import org.patchca.font.FontFactory;
import org.patchca.font.RandomFontFactory;
import org.patchca.service.CaptchaService;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.size.SingleSizeFactory;
import org.patchca.size.SizeFactory;
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

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author 应卓
 */
@Lazy(false)
@ConditionalOnProperty(prefix = "carnival.patchca", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnWebApplication
@EnableConfigurationProperties(PatchcaProps.class)
public class PatchcaBeanAutoConfig {

    // dao
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "carnival.patchca.servlet-filter", name = "enabled", havingValue = "true", matchIfMissing = true)
    public CaptchaDao captchaDao(PatchcaProps props) {
        final Mode mode = props.getMode();
        if (mode == Mode.STATEFUL) {
            return new HttpSessionCaptchaDao();
        } else {
            return new MapCaptchaDao();
        }
    }

    // 处理单元
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "carnival.patchca.servlet-filter", name = "enabled", havingValue = "true", matchIfMissing = true)
    public CaptchaHandler captchaHandler(PatchcaProps props) {
        final Mode mode = props.getMode();
        if (mode == Mode.STATEFUL) {
            return new DefaultStatefulCaptchaHandler();
        } else {
            return new DefaultStatelessCaptchaHandler();
        }
    }

    // 文本内容
    @Bean
    @ConditionalOnMissingBean
    public WordFactory wordFactory(PatchcaProps props) {
        val subProps = props.getWord();
        val bean = new AdaptiveRandomWordFactory();
        bean.setWideCharacters(subProps.getWideCharacters());
        bean.setCharacters(subProps.getCharacters());
        bean.setMaxLength(subProps.getMaxLength());
        bean.setMinLength(subProps.getMinLength());
        return bean;
    }

    // 背景
    @Bean
    @ConditionalOnMissingBean
    public BackgroundFactory backgroundFactory(PatchcaProps props) {
        val subProps = props.getBackground();
        val bean = new SingleColorBackgroundFactory();
        bean.setColorFactory(subProps.createColorFactory());
        return bean;
    }

    // 前景
    @Bean
    @ConditionalOnMissingBean
    public ColorFactory colorFactory(PatchcaProps props) {
        val subProps = props.getForeground();
        return subProps.createColorFactory();
    }

    // 字体
    @Bean
    @ConditionalOnMissingBean
    public FontFactory fontFactory(PatchcaProps props) {
        val subProps = props.getFont();
        val bean = new RandomFontFactory();
        bean.setFamilies(Arrays.asList(subProps.getFamilies()));
        bean.setMaxSize(subProps.getMaxSize());
        bean.setMinSize(subProps.getMinSize());
        return bean;
    }

    // 文本渲染
    @Bean
    @ConditionalOnMissingBean
    public TextRenderer textRenderer(PatchcaProps props) {
        val subProps = props.getTextRenderer();
        val bean = new BestFitTextRenderer();
        bean.setBottomMargin(subProps.getBottomMargin());
        bean.setTopMargin(subProps.getTopMargin());
        bean.setLeftMargin(subProps.getLeftMargin());
        bean.setRightMargin(subProps.getRightMargin());
        return bean;
    }

    // 图片尺寸
    @Bean
    @ConditionalOnMissingBean
    public SizeFactory sizeFactory(PatchcaProps props) {
        val subProps = props.getSize();
        return new SingleSizeFactory(subProps.getWidth(), subProps.getHeight());
    }

    // 滤镜
    @Bean
    @ConditionalOnMissingBean
    public FilterFactory filterFactory(PatchcaProps props, ColorFactory colorFactory) {
        val types = props.getFilter().getTypes();

        if (types == null || types.isEmpty()) {
            return new NoneFilterFactory();
        } else if (types.size() == 1 && types.get(0) == FilterType.NONE) {
            return new NoneFilterFactory();
        } else {
            val factories = new ArrayList<FilterFactory>();

            for (FilterType type : types) {
                switch (type) {
                    case NONE:
                        continue;
                    case CURVES:
                        factories.add(new CurvesAbstractRippleFilterFactory(colorFactory));
                        continue;
                    case DIFFUSE:
                        factories.add(new DiffuseAbstractRippleFilterFactory());
                        continue;
                    case DOUBLE:
                        factories.add(new DoubleRippleFilterFactory());
                        continue;
                    case MARBLE:
                        factories.add(new MarbleAbstractRippleFilterFactory());
                    case WOBBLE:
                        factories.add(new WobbleAbstractRippleFilterFactory());
                }
            }

            return factories.isEmpty() ? new NoneFilterFactory() : CompositeFilterFactory.of(factories);
        }
    }

    // 核心
    @Bean
    @ConditionalOnMissingBean
    public CaptchaService captchaService(
            BackgroundFactory backgroundFactory,
            ColorFactory colorFactory,
            FontFactory fontFactory,
            TextRenderer textRenderer,
            FilterFactory filterFactory,
            WordFactory wordFactory,
            SizeFactory sizeFactory
    ) {
        val bean = new ConfigurableCaptchaService();
        bean.setBackgroundFactory(backgroundFactory);
        bean.setFontFactory(fontFactory);
        bean.setTextRenderer(textRenderer);
        bean.setColorFactory(colorFactory);
        bean.setWordFactory(wordFactory);
        bean.setWidth(sizeFactory.getWidth());
        bean.setHeight(sizeFactory.getHeight());
        bean.setFilterFactory(filterFactory);
        return bean;
    }

}
