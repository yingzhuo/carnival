/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mustache.autoconfig;

import com.github.yingzhuo.carnival.mustache.MustacheCompiler;
import com.github.yingzhuo.carnival.mustache.impl.DefaultMustacheCompiler;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @since 1.1.8
 */
@EnableConfigurationProperties(MustacheCompilerAutoConfig.Props.class)
public class MustacheCompilerAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public MustacheCompiler mustacheCompiler(Props props) {
        final DefaultMustacheCompiler bean = new DefaultMustacheCompiler();
        bean.setCharset(props.getCharset());
        return bean;
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.mustache")
    static class Props  {
        private boolean enabled = true;
        private String charset = "UTF-8";
    }
}
