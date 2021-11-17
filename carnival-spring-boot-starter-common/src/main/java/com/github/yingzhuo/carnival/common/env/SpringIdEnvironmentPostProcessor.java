/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.env;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.Collections;
import java.util.UUID;

/**
 * @author 应卓
 * @since 1.8.4
 */
@Slf4j
class SpringIdEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        val springId = UUID.randomUUID().toString();
        val source = Collections.<String, Object>singletonMap("spring.id", springId);
        environment.getPropertySources()
                .addLast(new MapPropertySource("springId", source));
        log.info("spring.id = {}", springId);
    }

}
