/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.spring.autoconfig;

import com.github.yingzhuo.carnival.spring.SpringUtils;
import com.github.yingzhuo.carnival.spring.SpringUtilsApplicationContextAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

/**
 * @author 应卓
 * @see SpringUtils
 */
@Slf4j
public class SpringUtilsConfiguration {

    @PostConstruct
    private void init() {
        log.debug("SpringBoot auto-config: {}", getClass().getName());
    }

    @Bean
    public SpringUtilsApplicationContextAware springUtilsApplicationContextAware() {
        return SpringUtilsApplicationContextAware.INSTANCE;
    }

}
