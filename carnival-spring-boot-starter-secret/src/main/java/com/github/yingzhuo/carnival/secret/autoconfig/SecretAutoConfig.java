/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.secret.autoconfig;

import com.github.yingzhuo.carnival.secret.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.format.FormatterRegistry;

/**
 * @author 应卓
 */
@Slf4j
@EnableConfigurationProperties(SecretAutoConfig.Props.class)
public class SecretAutoConfig {

    @Autowired(required = false)
    public void config(FormatterRegistry registry) {
        if (registry != null) {
            registry.addFormatterForFieldAnnotation(new RSA.EncryptByPrivateKey.FormatterFactory());
            registry.addFormatterForFieldAnnotation(new RSA.EncryptByPublicKey.FormatterFactory());
            registry.addFormatterForFieldAnnotation(new RSA.DecryptByPrivateKey.FormatterFactory());
            registry.addFormatterForFieldAnnotation(new RSA.DecryptByPublicKey.FormatterFactory());
            registry.addFormatterForFieldAnnotation(new MD5.Encrypt.FormatterFactory());
            registry.addFormatterForFieldAnnotation(new MD2.Encrypt.FormatterFactory());
            registry.addFormatterForFieldAnnotation(new SHA1.Encrypt.FormatterFactory());
            registry.addFormatterForFieldAnnotation(new SHA256.Encrypt.FormatterFactory());
        }
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.rsa")
    public static class Props implements InitializingBean {
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
