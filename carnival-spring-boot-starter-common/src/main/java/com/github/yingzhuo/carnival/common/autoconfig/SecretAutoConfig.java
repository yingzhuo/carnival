/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.autoconfig;

import com.github.yingzhuo.carnival.common.Configurer;
import com.github.yingzhuo.carnival.common.io.ResourceText;
import com.github.yingzhuo.carnival.secret.*;
import com.github.yingzhuo.carnival.secret.rsa.RSAHelper;
import com.github.yingzhuo.carnival.secret.rsa.RSAKeyPair;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.format.FormatterRegistry;

import java.io.Serializable;

/**
 * @author 应卓
 */
@Slf4j
@EnableConfigurationProperties({
        SecretAutoConfig.SecretProps.class
})
@ConditionalOnProperty(prefix = "carnival.secret", name = "enabled", havingValue = "true")
@Lazy(false)
public class SecretAutoConfig implements Configurer<FormatterRegistry> {

    @Autowired
    private SecretProps props;

    @Override
    @Autowired
    public void config(FormatterRegistry registry) {
        val rsaKeyPair = RSAKeyPair.fromString(
                props.getRsa().getPublicKey(),
                props.getRsa().getPrivateKey()
        );

        val rsaHelper = RSAHelper.of(rsaKeyPair);

        registry.addFormatterForFieldAnnotation(new Base64.Encoding.FormatterFactory());
        registry.addFormatterForFieldAnnotation(new Base64.Decoding.FormatterFactory());
        registry.addFormatterForFieldAnnotation(new RSA.EncryptByPrivateKey.FormatterFactory(rsaHelper));
        registry.addFormatterForFieldAnnotation(new RSA.EncryptByPublicKey.FormatterFactory(rsaHelper));
        registry.addFormatterForFieldAnnotation(new RSA.DecryptByPrivateKey.FormatterFactory(rsaHelper));
        registry.addFormatterForFieldAnnotation(new RSA.DecryptByPublicKey.FormatterFactory(rsaHelper));
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
    static class SecretProps implements Serializable, InitializingBean {
        private boolean enabled = false;
        private RSAProps rsa = new RSAProps();

        @Override
        public void afterPropertiesSet() {
            if (this.enabled) {
                this.rsa.afterPropertiesSet();
            }
        }
    }

    @Getter
    @Setter
    static class RSAProps implements Serializable, InitializingBean {
        private String publicKey;
        private String privateKey;
        private ResourceText publicKeyLocation;
        private ResourceText privateKeyLocation;

        @Override
        public void afterPropertiesSet() {
            if (this.publicKeyLocation != null) {
                this.publicKey = this.publicKeyLocation.getTextAsOneLine().trim();
            }

            if (this.privateKeyLocation != null) {
                this.privateKey = this.privateKeyLocation.getTextAsOneLine().trim();
            }
        }
    }

}
