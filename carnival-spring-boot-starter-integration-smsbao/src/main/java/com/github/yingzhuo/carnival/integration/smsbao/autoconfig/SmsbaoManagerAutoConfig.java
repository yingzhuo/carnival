/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.integration.smsbao.autoconfig;

import com.github.yingzhuo.carnival.integration.smsbao.Mode;
import com.github.yingzhuo.carnival.integration.smsbao.SmsbaoManager;
import com.github.yingzhuo.carnival.integration.smsbao.impl.DefaultSmsbaoManagerImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

@Slf4j
@EnableConfigurationProperties(SmsbaoManagerAutoConfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.integration.smsbao", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SmsbaoManagerAutoConfig {

    @PostConstruct
    private void init() {
        log.debug("SpringBoot auto-config: {}", getClass().getName());
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate smsbaoRestTemplate() {
        RestTemplate bean = new RestTemplate();
        bean.getMessageConverters().forEach(c -> {
            if (c instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) c).setDefaultCharset(StandardCharsets.UTF_8);
            }
        });
        return bean;
    }

    @Bean
    @ConditionalOnMissingBean
    public SmsbaoManager smsbaoManager(Props props, RestTemplate restTemplate) {
        if (props.getMode() == Mode.NOP) {
            return (phoneNumber, content) -> true;
        } else {
            return new DefaultSmsbaoManagerImpl(restTemplate, props.getUsername(), props.getPassword());
        }
    }

    @Getter
    @Setter
    @ConfigurationProperties("carnival.integration.smsbao")
    static class Props {
        private boolean enabled = true;
        private Mode mode = Mode.REGULAR;
        private String username = null;
        private String password = null;

        @PostConstruct
        public void init() {
            if (enabled && Mode.REGULAR == getMode()) {
                if (StringUtils.isEmpty(getUsername())) throw new IllegalArgumentException();
                if (StringUtils.isEmpty(getPassword())) throw new IllegalArgumentException();
            }
        }
    }

}
