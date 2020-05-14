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
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author 应卓
 * @since 1.6.7
 */
public class XEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private final Pattern springIdPattern = Pattern.compile("[a-z].{31}");

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        final Map<String, Object> values = new HashMap<>();
        setSpringId(values);
        setMainClass(values, application);
        setJarDir(values, application);
        setWebApplicationType(values, application);

        environment.getPropertySources()
                .addFirst(new MapPropertySource("x", Collections.unmodifiableMap(values)));
    }

    private void setSpringId(Map<String, Object> map) {
        String id = UUID.randomUUID().toString().replaceAll("-", "");

        // 不要以数字开头的ID
        // 用递归随便写写算了
        if (!springIdPattern.matcher(id).matches()) {
            setSpringId(map);
        } else {
            map.put("x.spring.id", id);
        }
    }

    private void setMainClass(Map<String, Object> map, SpringApplication application) {
        try {
            String mainClass = application.getMainApplicationClass().getName();
            map.put("x.main.class", mainClass);
        } catch (Exception ignore) {
        }
    }

    private void setJarDir(Map<String, Object> map, SpringApplication application) {
        try {
            String jarDir = new ApplicationHome(application.getMainApplicationClass()).getDir().getAbsolutePath();
            map.put("x.jar.dir", jarDir);
        } catch (Exception ignore) {
        }
    }

    private void setWebApplicationType(Map<String, Object> map, SpringApplication application) {
        try {
            map.put("x.webapp.type", application.getWebApplicationType().toString());
        } catch (Exception ignore) {
        }
    }

}
