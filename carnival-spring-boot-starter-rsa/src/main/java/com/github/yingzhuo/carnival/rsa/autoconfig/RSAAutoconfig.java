/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.rsa.autoconfig;

import com.github.yingzhuo.carnival.rsa.RSA;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.format.FormatterRegistry;

/**
 * @author 应卓
 */
@Slf4j
@EnableConfigurationProperties(RSAAutoconfig.Props.class)
@ConditionalOnProperty(prefix = "carnival.rsa", name = "enabled", havingValue = "true", matchIfMissing = true)
public class RSAAutoconfig {

    @Autowired(required = false)
    public void config(FormatterRegistry registry) {
        if (registry != null) {
            registry.addFormatterForFieldAnnotation(new RSA.DecryptByPrivateKey.FormatterFactory());
            registry.addFormatterForFieldAnnotation(new RSA.DecryptByPublicKey.FormatterFactory());
        }
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.rsa")
    public static class Props implements InitializingBean {
        private boolean enabled = true;
        private String publicKey;
        private String privateKey;

        @Override
        public void afterPropertiesSet() {
            if (StringUtils.isBlank(publicKey)) {
                log.warn("public-key is blank.");
            }
            if (StringUtils.isBlank(privateKey)) {
                log.warn("private-key is blank.");
            }
        }
    }

}
