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

import com.github.yingzhuo.springboot.env.postprocessor.AbstractConventionEnvironmentPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author 应卓
 * @since 1.7.14
 */
public class KubernetesConfigMapEnvironmentPostProcessor extends AbstractConventionEnvironmentPostProcessor {

    private static final String NAME = "kubernetes-config-map";

    @Override
    protected String getName(ConfigurableEnvironment environment, SpringApplication application) {
        return NAME;
    }

    @Override
    protected String[] getLocationsPrefix(ConfigurableEnvironment environment, SpringApplication application) {
        return new String[]{
                "file:/home/java/config/k8s",
                "file:/home/java/config/kubernetes",
                "file:/home/java/config/configmap",
                "file:/home/java/config/config-map",
                "file:/home/java/k8s",
                "file:/home/java/kubernetes",
                "file:/home/java/configmap",
                "file:/home/java/config-map",
                "file:/home/spring/config/k8s",
                "file:/home/spring/config/kubernetes",
                "file:/home/spring/config/configmap",
                "file:/home/spring/config/config-map",
                "file:/home/spring/k8s",
                "file:/home/spring/kubernetes",
                "file:/home/spring/configmap",
                "file:/home/spring/config-map"
        };
    }

}
