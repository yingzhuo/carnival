/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.apigateway.autoconfig;

import com.github.yingzhuo.carnival.apigateway.filter.LoggingFilter;
import com.github.yingzhuo.carnival.common.condition.ConditionalOnDebugMode;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
public class LoggingFilterAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnDebugMode
    public LoggingFilter loggingFilter() {
        return new LoggingFilter();
    }

}
