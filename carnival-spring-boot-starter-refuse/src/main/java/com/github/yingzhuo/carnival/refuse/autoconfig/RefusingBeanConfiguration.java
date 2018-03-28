/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.refuse.autoconfig;

import com.github.yingzhuo.carnival.refuse.RefusingConfig;
import com.github.yingzhuo.carnival.refuse.RefusingConfigLoader;
import com.github.yingzhuo.carnival.refuse.RefusingListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
@Slf4j
public class RefusingBeanConfiguration {

    @PostConstruct
    private void init() {
        log.debug("SpringBoot auto-config: {}", getClass().getName());
    }

    @Bean
    @ConditionalOnMissingBean
    public RefusingConfigLoader refuseConfigLoader() {
        return RefusingConfig::new;
    }

    @Bean
    @ConditionalOnMissingBean
    public RefusingListener refuseListener() {
        return refuseContext -> {};
    }

}
