/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.autoconfig;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "server.http", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(MvcBothPortAutoConfig.Props.class)
public class MvcBothPortAutoConfig {

    @Autowired
    private Props props;

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        val bean = new TomcatServletWebServerFactory();
        bean.addAdditionalTomcatConnectors(connector());
        return bean;
    }

    private Connector connector() {
        val connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(props.getPort());
        return connector;
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "server.http")
    static class Props {
        private boolean enabled = false;
        private int port = -1;
    }

}
