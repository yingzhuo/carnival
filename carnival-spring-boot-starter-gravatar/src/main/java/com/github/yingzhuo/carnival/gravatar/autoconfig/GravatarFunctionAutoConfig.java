/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.gravatar.autoconfig;

import com.github.yingzhuo.carnival.gravatar.DefaultImage;
import com.github.yingzhuo.carnival.gravatar.GravatarFactory;
import com.github.yingzhuo.carnival.gravatar.DefaultGravatarFactory;
import com.github.yingzhuo.carnival.gravatar.Rating;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
@EnableConfigurationProperties(GravatarFunctionAutoConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.gravatar", name = "enabled", havingValue = "true", matchIfMissing = true)
public class GravatarFunctionAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public GravatarFactory gravatarFunction(Props props) {
        DefaultGravatarFactory function = new DefaultGravatarFactory();
        function.setDefaultImage(props.defaultImage);
        function.setRating(props.rating);
        function.setPrefix(props.getPrefix());
        function.setSuffix(props.getSuffix());
        return function;
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.gravatar")
    public static class Props {
        private boolean enabled = true;
        private DefaultImage defaultImage = DefaultImage.IDENTICON;
        private Rating rating = Rating.GENERAL_AUDIENCE;
        private String prefix = "http://www.gravatar.com/avatar/";
        private String suffix = ".jpg";
    }

}
