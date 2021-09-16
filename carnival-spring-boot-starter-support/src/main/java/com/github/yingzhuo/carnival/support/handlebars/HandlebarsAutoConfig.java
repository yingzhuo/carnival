/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.support.handlebars;

import com.github.jknack.handlebars.Handlebars;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author 应卓
 * @since 1.10.20
 */
@EnableConfigurationProperties(HandlebarsAutoConfig.Props.class)
@ConditionalOnClass(name = "com.github.jknack.handlebars.Handlebars")
@ConditionalOnMissingBean(name = "com.github.jknack.handlebars.Handlebars")
@ConditionalOnProperty(prefix = "carnival.support.handlebars", name = "enabled", havingValue = "true", matchIfMissing = false)
class HandlebarsAutoConfig {

    @Bean
    HandlebarsFactoryBean handlebarsFactoryBean(Props props) {
        return new HandlebarsFactoryBean(props.getPrefix(), props.getSuffix(), props.getCharset());
    }

    @Bean
    TemplatesFactoryBean templatesFactoryBean(Handlebars handlebars, Props props) {
        return new TemplatesFactoryBean(handlebars, props.getTemplates());
    }

    @Getter
    @Setter
    @ConditionalOnProperty(prefix = "carnival.support.handlebars")
    static class Props {
        private boolean enabled = false;
        private String prefix = "/handlebars";
        private String suffix = ".hbs";
        private String[] templates;
        private Charset charset = StandardCharsets.UTF_8;
    }

}
