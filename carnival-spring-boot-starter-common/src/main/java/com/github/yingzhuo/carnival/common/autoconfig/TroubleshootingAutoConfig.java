/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.autoconfig;

import com.github.yingzhuo.carnival.common.condition.ConditionalOnDebug;
import com.github.yingzhuo.carnival.troubleshooting.EnvLoggingApplicationRunner;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @since 1.6.24
 */
public class TroubleshootingAutoConfig {

    @Bean
    @ConditionalOnDebug
    public EnvLoggingApplicationRunner envLoggingApplicationRunner() {
        return new EnvLoggingApplicationRunner();
    }

}
