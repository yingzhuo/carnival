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
@Lazy(false)
@EnableConfigurationProperties(SecretAutoConfig.SecretProps.class)
@ConditionalOnProperty(prefix = "carnival.secret", name = "enabled", havingValue = "true")
public class SecretAutoConfig implements Configurer<FormatterRegistry> {

    @Autowired
    private SecretProps props;

    @Override
    @Autowired
    public void config(FormatterRegistry registry) {

        // RSA
        val rsaProps = props.getRsa();
        if (rsaProps.isEnabled()) {
            val rsaKeyPair = RSAKeyPair.fromString(
                    props.getRsa().getPublicKey(),
                    props.getRsa().getPrivateKey()
            );
            val rsaHelper = RSAHelper.of(rsaKeyPair);
            registry.addFormatterForFieldAnnotation(new RSA.EncryptingByPrivateKey.FormatterFactory(rsaHelper));
            registry.addFormatterForFieldAnnotation(new RSA.EncryptingByPublicKey.FormatterFactory(rsaHelper));
            registry.addFormatterForFieldAnnotation(new RSA.DecryptingByPrivateKey.FormatterFactory(rsaHelper));
            registry.addFormatterForFieldAnnotation(new RSA.DecryptingByPublicKey.FormatterFactory(rsaHelper));
        }

        val aesProps = props.getAes();
        if (aesProps.isEnabled()) {
            registry.addFormatterForFieldAnnotation(new AES.Encrypting.FormatterFactory(aesProps.getPassphrase()));
            registry.addFormatterForFieldAnnotation(new AES.Decrypting.FormatterFactory(aesProps.getPassphrase()));
        }

        // BASE64
        registry.addFormatterForFieldAnnotation(new Base64.Encoding.FormatterFactory());
        registry.addFormatterForFieldAnnotation(new Base64.Decoding.FormatterFactory());

        // MD2, MD5
        registry.addFormatterForFieldAnnotation(new MD5.Encrypting.FormatterFactory());
        registry.addFormatterForFieldAnnotation(new MD2.Encrypting.FormatterFactory());

        // SHA-xxx
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
        private AESProps aes = new AESProps();

        @Override
        public void afterPropertiesSet() {
            if (enabled) {
                rsa.afterPropertiesSet();
                aes.afterPropertiesSet();
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

        public boolean isEnabled() {
            return publicKey != null && privateKey != null;
        }

        @Override
        public void afterPropertiesSet() {
            if (publicKeyLocation != null) {
                publicKey = publicKeyLocation.getTextAsOneLineAndTrim();
            }

            if (privateKeyLocation != null) {
                privateKey = privateKeyLocation.getTextAsOneLineAndTrim();
            }
        }
    }

    @Getter
    @Setter
    static class AESProps implements Serializable, InitializingBean {
        private String passphrase;
        private ResourceText passphraseLocation;

        public boolean isEnabled() {
            return passphrase != null;
        }

        @Override
        public void afterPropertiesSet() {
            if (passphraseLocation != null) {
                passphrase = passphraseLocation.getTextAsOneLineAndTrim();
            }
        }
    }

}
