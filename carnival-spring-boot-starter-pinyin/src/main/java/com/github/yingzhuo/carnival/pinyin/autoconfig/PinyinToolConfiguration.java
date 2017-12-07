/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.pinyin.autoconfig;

import com.github.yingzhuo.carnival.pinyin.PinyinTool;
import com.github.yingzhuo.carnival.pinyin.impl.SimplePinyinTool;
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
@Slf4j
@EnableConfigurationProperties(PinyinToolConfiguration.Props.class)
@ConditionalOnProperty(prefix = "carnival.pinyin", name = "enabled", havingValue = "true", matchIfMissing = true)
public class PinyinToolConfiguration {

    @PostConstruct
    private void init() {
        log.debug("SpringBoot auto-config: {}", getClass().getName());
    }

    @Bean
    @ConditionalOnMissingBean
    public PinyinTool pinyinTool() {
        return new SimplePinyinTool();
    }

    @Data
    @ConfigurationProperties("carnival.pinyin")
    static class Props {
        private boolean enabled = true;
    }

}
