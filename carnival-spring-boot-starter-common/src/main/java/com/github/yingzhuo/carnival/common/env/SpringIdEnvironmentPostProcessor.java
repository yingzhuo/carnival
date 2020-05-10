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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.Collections;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author 应卓
 * @since 1.6.5
 */
public class SpringIdEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private final Pattern pattern = Pattern.compile("[a-z].{31}");

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        final String id = createRandomId();
        environment.getPropertySources()
                .addFirst(
                        new MapPropertySource(
                                "spring.id",
                                Collections.singletonMap("spring.id", id)
                        )
                );
    }

    private String createRandomId() {
        String id = UUID.randomUUID().toString().replaceAll("-", "");

        // 不要以数字开头的ID
        // 用递归随便写写算了
        if (!pattern.matcher(id).matches()) {
            id = createRandomId();
        }

        return id;
    }
}
