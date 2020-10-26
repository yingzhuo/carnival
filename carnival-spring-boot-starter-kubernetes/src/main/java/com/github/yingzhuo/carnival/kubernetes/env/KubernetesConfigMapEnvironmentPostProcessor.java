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
                "file:/home/app/config/k8s",
                "file:/home/app/config/kubernetes",
                "file:/home/app/config/configmap",
                "file:/home/app/config/config-map"
        };
    }

}
