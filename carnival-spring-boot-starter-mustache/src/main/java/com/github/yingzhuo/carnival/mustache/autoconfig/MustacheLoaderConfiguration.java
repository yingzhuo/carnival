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

import com.github.yingzhuo.carnival.mustache.MustacheLoader;
import com.github.yingzhuo.carnival.mustache.impl.MustacheLoaderImpl;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
@EnableConfigurationProperties(MustacheLoaderConfiguration.Props.class)
@ConditionalOnProperty(prefix = "carnival.mustache", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MustacheLoaderConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MustacheLoader mustacheLoader() {
        return new MustacheLoaderImpl("UTF-8");
    }

    @Data
    @ConfigurationProperties("carnival.mustache")
    static class Props {
        private boolean enabled = true;
    }
}
