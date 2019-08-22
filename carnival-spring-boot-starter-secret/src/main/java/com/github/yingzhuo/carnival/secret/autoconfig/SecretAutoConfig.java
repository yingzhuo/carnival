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

import com.github.yingzhuo.carnival.common.io.ResourceToLine;
import com.github.yingzhuo.carnival.secret.*;
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
@EnableConfigurationProperties({
        SecretAutoConfig.SecretProps.class,
        SecretAutoConfig.AESProps.class,
        SecretAutoConfig.RSAProps.class
})
@ConditionalOnProperty(prefix = "carnival.secret", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SecretAutoConfig {

    @Autowired
    public void config(FormatterRegistry registry) {
        registry.addFormatterForFieldAnnotation(new Base64.Encoding.FormatterFactory());
        registry.addFormatterForFieldAnnotation(new Base64.Decoding.FormatterFactory());
        registry.addFormatterForFieldAnnotation(new RSA.EncryptByPrivateKey.FormatterFactory());
        registry.addFormatterForFieldAnnotation(new RSA.EncryptByPublicKey.FormatterFactory());
        registry.addFormatterForFieldAnnotation(new RSA.DecryptByPrivateKey.FormatterFactory());
        registry.addFormatterForFieldAnnotation(new RSA.DecryptByPublicKey.FormatterFactory());
        registry.addFormatterForFieldAnnotation(new AES.Encrypting.FormatterFactory());
        registry.addFormatterForFieldAnnotation(new AES.Decrypting.FormatterFactory());
        registry.addFormatterForFieldAnnotation(new MD5.Encrypting.FormatterFactory());
        registry.addFormatterForFieldAnnotation(new MD2.Encrypting.FormatterFactory());
        registry.addFormatterForFieldAnnotation(new SHA1.Encrypting.FormatterFactory());
        registry.addFormatterForFieldAnnotation(new SHA256.Encrypting.FormatterFactory());
        registry.addFormatterForFieldAnnotation(new SHA384.Encrypting.FormatterFactory());
        registry.addFormatterForFieldAnnotation(new SHA512.Encrypting.FormatterFactory());
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.secret")
    static class SecretProps {
        private boolean enabled = true;
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.secret.rsa")
    static class RSAProps implements InitializingBean {

        private String publicKey;
        private String privateKey;
        private String publicKeyLocation;
        private String privateKeyLocation;

        @Override
        public void afterPropertiesSet() {
            if (StringUtils.isBlank(publicKey) && StringUtils.isNotBlank(publicKeyLocation)) {
                this.publicKey = ResourceToLine.apply(publicKeyLocation).trim();
            }

            if (StringUtils.isBlank(privateKey) && StringUtils.isNotBlank(privateKeyLocation)) {
                this.privateKey = ResourceToLine.apply(privateKeyLocation).trim();
            }
        }
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.secret.aes")
    static class AESProps implements InitializingBean {

        private String passphrase;
        private String passphraseLocation;

        @Override
        public void afterPropertiesSet() {
            if (StringUtils.isBlank(passphrase) && StringUtils.isNotBlank(passphraseLocation)) {
                this.passphrase = ResourceToLine.apply(passphraseLocation).trim();
            }
        }
    }
}
