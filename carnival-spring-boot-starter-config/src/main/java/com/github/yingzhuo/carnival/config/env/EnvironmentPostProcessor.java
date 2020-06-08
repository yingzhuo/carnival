/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.config.env;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author 应卓
 * @since 1.6.15
 */
public class EnvironmentPostProcessor implements org.springframework.boot.env.EnvironmentPostProcessor {

    private final Pattern springIdPattern = Pattern.compile("[a-z].{31}");

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication app) {

        final Map<String, Object> values = new TreeMap<>();
        setSpringId(values);
        setStartupTime(values);
        setMainClass(values, app);
        setJarDir(values, app);
        setWebApplicationType(values, app);

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

    private void setMainClass(Map<String, Object> map, SpringApplication app) {
        try {
            String mainClass = app.getMainApplicationClass().getName();
            map.put("x.main.class", mainClass);
        } catch (Exception ignore) {
        }
    }

    private void setJarDir(Map<String, Object> map, SpringApplication app) {
        try {
            String jarDir = new ApplicationHome(app.getMainApplicationClass()).getDir().getAbsolutePath();
            map.put("x.jar.dir", jarDir);
        } catch (Exception ignore) {
        }
    }

    private void setWebApplicationType(Map<String, Object> map, SpringApplication app) {
        try {
            map.put("x.webapp.type", app.getWebApplicationType().toString());
        } catch (Exception ignore) {
        }
    }

    private void setStartupTime(Map<String, Object> map) {
        try {
            final Date date = new Date();
            final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            map.put("x.startup.timestamp", date.getTime());
            map.put("x.startup.time", dateFormat.format(date));
        } catch (Exception ignored) {
        }
    }

}
