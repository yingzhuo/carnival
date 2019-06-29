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
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.format.FormatterRegistry;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author 应卓
 */
@Slf4j
@EnableConfigurationProperties(SecretAutoConfig.Props.class)
public class SecretAutoConfig {

    @Autowired(required = false)
    public void config(FormatterRegistry registry) {
        if (registry != null) {
            registry.addFormatterForFieldAnnotation(new Base64.Encoding.FormatterFactory());
            registry.addFormatterForFieldAnnotation(new Base64.Decoding.FormatterFactory());
            registry.addFormatterForFieldAnnotation(new RSA.EncryptByPrivateKey.FormatterFactory());
            registry.addFormatterForFieldAnnotation(new RSA.EncryptByPublicKey.FormatterFactory());
            registry.addFormatterForFieldAnnotation(new RSA.DecryptByPrivateKey.FormatterFactory());
            registry.addFormatterForFieldAnnotation(new RSA.DecryptByPublicKey.FormatterFactory());
            registry.addFormatterForFieldAnnotation(new MD5.Encrypting.FormatterFactory());
            registry.addFormatterForFieldAnnotation(new MD2.Encrypting.FormatterFactory());
            registry.addFormatterForFieldAnnotation(new SHA1.Encrypting.FormatterFactory());
            registry.addFormatterForFieldAnnotation(new SHA256.Encrypting.FormatterFactory());
            registry.addFormatterForFieldAnnotation(new SHA384.Encrypting.FormatterFactory());
            registry.addFormatterForFieldAnnotation(new SHA512.Encrypting.FormatterFactory());
        }
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.rsa")
    public static class Props implements InitializingBean {

        private final ResourceLoader resourceLoader = new DefaultResourceLoader();

        private String publicKey;
        private String privateKey;
        private String publicKeyLocation;
        private String privateKeyLocation;

        @Override
        public void afterPropertiesSet() {
            if (StringUtils.isBlank(publicKey) && StringUtils.isNotBlank(publicKeyLocation)) {
                this.publicKey = init(publicKeyLocation);
            }

            if (StringUtils.isBlank(privateKey) && StringUtils.isNotBlank(privateKeyLocation)) {
                this.privateKey = init(privateKeyLocation);
            }

            if (publicKey != null) {
                publicKey = publicKey.trim();
            }

            if (privateKey != null) {
                privateKey = privateKey.trim();
            }
        }

        private String init(String location) {
            try {
                val resource = resourceLoader.getResource(publicKeyLocation);
                val lines = IOUtils.readLines(resource.getInputStream(), StandardCharsets.UTF_8);
                val result = StringUtils.join(lines, "");
                resource.getInputStream().close();
                return result;
            } catch (IOException ignored) {
                return null;
            }
        }
    }

}
