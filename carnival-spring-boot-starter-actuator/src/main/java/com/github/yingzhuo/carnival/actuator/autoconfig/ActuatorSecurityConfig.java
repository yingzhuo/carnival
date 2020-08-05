/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.actuator.autoconfig;

import com.github.yingzhuo.carnival.actuator.security.ActuatorSecurityFilter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 应卓
 * @since 1.6.33
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(prefix = "carnival.actuator.security", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(ActuatorSecurityConfig.Props.class)
public class ActuatorSecurityConfig {

    @Bean
    @ConfigurationPropertiesBinding
    public StringToActuatorSecurityUserConverter stringToActuatorSecurityUserConverter() {
        return new StringToActuatorSecurityUserConverter();
    }

    @Bean
    public FilterRegistrationBean<ActuatorSecurityFilter> actuatorSecurityFilterFilter(Props props) {
        FilterRegistrationBean<ActuatorSecurityFilter> bean = new FilterRegistrationBean<>(new ActuatorSecurityFilter(props.getUsers()));
        bean.addUrlPatterns(props.getActuatorPaths());
        bean.setName(ActuatorSecurityFilter.class.getName());
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.actuator.security")
    static class Props {
        private boolean enabled = false;
        private Set<ActuatorSecurityFilter.User> users = new HashSet<>();
        private String[] actuatorPaths = new String[]{"/actuator", "/actuator/*"};

        public Props() {
            ActuatorSecurityFilter.User user = new ActuatorSecurityFilter.User();
            user.setUsername("actuator");
            user.setPassword("actuator");
            users.add(user);
        }
    }

    static class StringToActuatorSecurityUserConverter implements Converter<String, ActuatorSecurityFilter.User> {
        @Override
        public ActuatorSecurityFilter.User convert(String source) {
            String[] parts = source.trim().split(":", 2);
            ActuatorSecurityFilter.User user = new ActuatorSecurityFilter.User();
            user.setUsername(parts[0]);
            user.setPassword(parts[1]);
            return user;
        }
    }

}
