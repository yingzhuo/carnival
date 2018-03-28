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
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

/**
 * @author 应卓
 */
@EnableConfigurationProperties(MustacheLoaderConfiguration.Props.class)
@ConditionalOnProperty(prefix = "carnival.mustache", name = "enabled", havingValue = "true", matchIfMissing = true)
@Slf4j
public class MustacheLoaderConfiguration {

    @PostConstruct
    private void init() {
        log.debug("SpringBoot auto-config: {}", getClass().getName());
    }

    @Bean
    @ConditionalOnMissingBean
    public MustacheLoader mustacheLoader(Props props) {
        return new MustacheLoaderImpl(props.getEncoding());
    }

    @Data
    @ConfigurationProperties("carnival.mustache")
    static class Props {
        private boolean enabled = true;
        private String encoding = "UTF-8";
    }
}
