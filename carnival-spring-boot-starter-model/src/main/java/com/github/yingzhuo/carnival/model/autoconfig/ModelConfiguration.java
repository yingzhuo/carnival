/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.model.autoconfig;

import com.github.yingzhuo.carnival.model.support.GenderFormatter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.FormatterRegistry;

import javax.annotation.PostConstruct;

/**
 * @author 应卓
 */
@Slf4j
@ConditionalOnProperty(prefix = "carnival.model", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ModelConfiguration {

    @PostConstruct
    private void init() {
        log.debug("SpringBoot auto-config: {}", getClass().getName());
    }

    @Autowired
    public void configFormatters(FormatterRegistry registry) {
        registry.addFormatter(GenderFormatter.INSTANCE);
    }

    @Data
    @ConfigurationProperties("carnival.model")
    static class Props {
        private boolean enabled = true;
    }
}
