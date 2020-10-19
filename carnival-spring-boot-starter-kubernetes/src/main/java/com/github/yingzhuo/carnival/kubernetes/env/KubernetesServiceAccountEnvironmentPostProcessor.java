/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.kubernetes.env;

import com.github.yingzhuo.carnival.common.io.ResourceText;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 * @since 1.7.14
 */
public class KubernetesServiceAccountEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final String K8S_SERVICEACCOUNT_PATH = "file:/var/run/secrets/kubernetes.io/serviceaccount/";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        final Map<String, Object> map = new HashMap<>();
        map.put("ca", getCa());
        map.put("namespace", getNamespace());
        map.put("token", getToken());
        environment.getPropertySources()
                .addLast(new MapPropertySource("serviceaccount", map));
    }

    private String getCa() {
        try {
            return ResourceText.of(K8S_SERVICEACCOUNT_PATH + "ca.crt").getText();
        } catch (UncheckedIOException e) {
            // 找不到资源
            return "";
        }
    }

    private String getNamespace() {
        try {
            return ResourceText.of(K8S_SERVICEACCOUNT_PATH + "namespace").getText();
        } catch (UncheckedIOException e) {
            // 找不到资源
            return "";
        }
    }

    private String getToken() {
        try {
            return ResourceText.of(K8S_SERVICEACCOUNT_PATH + "token").getText();
        } catch (UncheckedIOException e) {
            // 找不到资源
            return "";
        }
    }

}
