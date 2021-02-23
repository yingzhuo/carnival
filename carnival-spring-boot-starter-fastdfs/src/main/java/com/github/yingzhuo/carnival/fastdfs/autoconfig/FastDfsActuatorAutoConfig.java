/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.autoconfig;

import com.github.yingzhuo.carnival.fastdfs.actuator.FastDFSHealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @since 1.6.10
 */
@ConditionalOnClass(name = "org.springframework.boot.actuate.endpoint.annotation.Endpoint")
@ConditionalOnProperty(prefix = "carnival.fastdfs", name = "enabled", havingValue = "true", matchIfMissing = true)
public class FastDfsActuatorAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public FastDFSHealthIndicator fastDFSHealthIndicator() {
        return new FastDFSHealthIndicator();
    }

}
