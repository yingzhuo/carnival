/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.ssh2.autoconfig;

import com.github.yingzhuo.carnival.ssh2.ShellExecutor;
import com.github.yingzhuo.carnival.ssh2.impl.DefaultShellExecutor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

/**
 * @author 应卓
 */
@Slf4j
@EnableConfigurationProperties(ShellExecutorConfiguration.Props.class)
@ConditionalOnProperty(prefix = "carnival.ssh2", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ShellExecutorConfiguration {

    @PostConstruct
    private void init() {
        log.debug("SpringBoot auto-config: {}", getClass().getName());
    }

    @Bean
    @ConditionalOnMissingBean
    public ShellExecutor shellExecutor(Props props) {
        DefaultShellExecutor bean = new DefaultShellExecutor();
        bean.setHostname(props.getHostname());
        bean.setPort(props.getPort());
        bean.setUsername(props.getUsername());
        bean.setPassword(props.getPassword());
        return bean;
    }

    @Data
    @ConfigurationProperties("carnival.ssh2")
    static class Props implements InitializingBean {
        private boolean enabled = true;
        private String hostname = "127.0.0.1";
        private Integer port = 22;
        private String username = "root";
        private String password = "";

        @Override
        public void afterPropertiesSet() throws Exception {
            Assert.hasText(hostname, null);
            Assert.hasText(username, null);
            Assert.notNull(port, null);
            if (password == null) {
                password = "";
            }
        }
    }

}
