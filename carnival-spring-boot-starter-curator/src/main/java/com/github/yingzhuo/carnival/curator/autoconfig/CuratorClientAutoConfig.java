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
import com.github.yingzhuo.carnival.curator.props.CuratorClientProps;
import lombok.val;
import org.apache.curator.RetryPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @since 1.3.0
 */
@EnableConfigurationProperties(CuratorClientProps.class)
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
        factoryBean.setRetryPolicy(retryPolicy != null ? retryPolicy : props.getRetry().toRetryPolicy());
        return factoryBean;
    }

}
