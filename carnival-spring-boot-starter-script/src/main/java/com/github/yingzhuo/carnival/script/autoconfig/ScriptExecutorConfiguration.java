/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.script.autoconfig;

import com.github.yingzhuo.carnival.script.ScriptExecutor;
import com.github.yingzhuo.carnival.script.impl.SimpleScriptExecutor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@Slf4j
@EnableConfigurationProperties(ScriptExecutorConfiguration.Props.class)
@ConditionalOnProperty(prefix = "carnival.script", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ScriptExecutorConfiguration {

    @PostConstruct
    private void init() {
        log.debug("SpringBoot auto-config: {}", getClass().getName());
    }

    @Bean
    @ConditionalOnMissingBean(ScriptExecutor.class)
    public ScriptExecutor scriptExecutor() {
        return new SimpleScriptExecutor();
    }

    @Data
    @ConfigurationProperties(prefix = "carnival.script")
    static class Props {
        private boolean enabled = true;
    }

}
