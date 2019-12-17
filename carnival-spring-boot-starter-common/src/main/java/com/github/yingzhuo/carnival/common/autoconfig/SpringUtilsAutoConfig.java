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

import com.github.yingzhuo.carnival.spring.ApplicationContextProvider;
import com.github.yingzhuo.carnival.spring.SpringIdFactoryBean;
import com.github.yingzhuo.carnival.spring.SpringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.UUID;

/**
 * @author 应卓
 * @since 1.0.0
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpringUtilsAutoConfig implements Ordered {

    @Bean
    @ConditionalOnMissingBean
    public ApplicationContextProvider applicationContextProvider() {
        return ApplicationContextProvider.INSTANCE;
    }

    @Primary
    @Bean(name = SpringUtils.__IDENTITY__)
    public SpringIdFactoryBean identity() {
        return () -> UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
