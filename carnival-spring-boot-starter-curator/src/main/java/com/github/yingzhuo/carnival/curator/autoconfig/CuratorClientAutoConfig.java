/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.curator.autoconfig;

import com.github.yingzhuo.carnival.curator.CuratorFrameworkFactoryBean;
import com.github.yingzhuo.carnival.curator.CuratorFrameworkFactoryBuilderConfigurer;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.apache.curator.RetryPolicy;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author 应卓
 * @since 1.3.0
 */
@EnableConfigurationProperties(CuratorClientAutoConfig.CuratorClientProps.class)
@ConditionalOnProperty(prefix = "carnival.curator", name = "enabled", havingValue = "true", matchIfMissing = true)
public class CuratorClientAutoConfig {

    @Autowired(required = false)
    private RetryPolicy retryPolicy;

    @Autowired(required = false)
    private CuratorFrameworkFactoryBuilderConfigurer configurer;

    @Bean
    @ConditionalOnMissingBean
    public CuratorFrameworkFactoryBean curatorFramework(CuratorClientProps props) {
        val factoryBean = new CuratorFrameworkFactoryBean();
        factoryBean.setConnectString(props.getConnectString());
        factoryBean.setConnectionTimeoutMs(props.getConnectionTimeoutMs());
        factoryBean.setSessionTimeoutMs(props.getSessionTimeoutMs());
        factoryBean.setNamespace(props.getNamespace());
        factoryBean.setConfigurer(configurer);
        factoryBean.setRetryPolicy(retryPolicy != null ? retryPolicy : props.getRetry().createRetryPolicy());
        return factoryBean;
    }

    // --------------------------------------------------------------------------------------------------------------

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.curator")
    public static class CuratorClientProps implements InitializingBean {
        private boolean enabled = true;
        private String connectString;
        private int sessionTimeoutMs = 60000;
        private int connectionTimeoutMs = 60000;
        private String namespace;

        private RetryProps retry = new RetryProps();

        @Override
        public void afterPropertiesSet() {
            Assert.hasText(connectString, () -> null);

            if (!StringUtils.hasText(namespace)) {
                namespace = null;
            }
        }
    }

    @Getter
    @Setter
    public static class RetryProps {
        private int baseSleepTimeMs = 1000;
        private int maxRetries = 3;
        private int maxSleepMs = Integer.MAX_VALUE;

        public RetryPolicy createRetryPolicy() {
            return new ExponentialBackoffRetry(
                    baseSleepTimeMs,
                    maxRetries,
                    maxSleepMs);
        }
    }

}
