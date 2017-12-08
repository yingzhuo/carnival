/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.hook.autoconfig;

import com.github.yingzhuo.carnival.hook.support.HookAdvice;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

/**
 * @author 应卓
 */
@Slf4j
@EnableConfigurationProperties(HookConfiguration.Props.class)
@ConditionalOnProperty(prefix = "carnival.hook", name = "enabled", havingValue = "true", matchIfMissing = true)
public class HookConfiguration {

    @PostConstruct
    private void init() {
        log.debug("SpringBoot auto-config: {}", getClass().getName());
    }

    @Bean
    public HookAdvice hookAdvice() {
        return new HookAdvice();
    }

    @Data
    @ConfigurationProperties("carnival.hook")
    static class Props {
        private boolean enabled = true;
    }

}
