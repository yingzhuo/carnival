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

import com.github.yingzhuo.springboot.env.postprocessor.AbstractPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author 应卓
 * @since 1.7.16
 */
public class KubernetesSecretEnvironmentPostProcessor extends AbstractPostProcessor {

    @Override
    protected String getName(ConfigurableEnvironment environment, SpringApplication application) {
        return "kubernetes-secret";
    }

    @Override
    protected String[] getLocationsPrefix(ConfigurableEnvironment environment, SpringApplication application) {
        return new String[]{
                "file:/home/app/config/secret"
        };
    }

}
