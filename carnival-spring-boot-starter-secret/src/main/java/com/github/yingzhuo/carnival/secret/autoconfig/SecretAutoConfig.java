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

        private final ResourceLoader resourceLoader = new DefaultResourceLoader();

        @Deprecated
        private String publicKey;

        @Deprecated
        private String privateKey;

        private String publicKeyResource;

        private String privateKeyResource;

        @Override
        public void afterPropertiesSet() {
            if (StringUtils.isBlank(publicKey) && StringUtils.isBlank(publicKeyResource)) {
                log.warn("public-key is blank.");
            }
            if (StringUtils.isBlank(privateKey) && StringUtils.isBlank(privateKeyResource)) {
                log.warn("private-key is blank.");
            }

            init();
        }

        @SuppressWarnings("Duplicates")
        private void init() {
            if (StringUtils.isBlank(publicKey) && StringUtils.isNotBlank(publicKeyResource)) {
                try {
                    val resource = resourceLoader.getResource(publicKeyResource);
                    val lines = IOUtils.readLines(resource.getInputStream(), StandardCharsets.UTF_8);
                    publicKey = StringUtils.join(lines, "");
                    resource.getInputStream().close();
                } catch (IOException ignored) {
                }
            }

            if (StringUtils.isBlank(privateKey) && StringUtils.isNotBlank(privateKeyResource)) {
                try {
                    val resource = resourceLoader.getResource(privateKeyResource);
                    val lines = IOUtils.readLines(resource.getInputStream(), StandardCharsets.UTF_8);
                    privateKey = StringUtils.join(lines, "");
                    resource.getInputStream().close();
                } catch (IOException ignored) {
                }
            }
        }
    }

}
