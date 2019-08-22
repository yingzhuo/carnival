/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.secret;

import com.github.yingzhuo.carnival.common.datamodel.support.NopStringFormatter;
import com.github.yingzhuo.carnival.secret.autoconfig.SecretAutoConfig;
import com.github.yingzhuo.carnival.secret.util.RSAUtils;
import com.github.yingzhuo.carnival.spring.SpringUtils;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.lang.annotation.*;
import java.util.Collections;
import java.util.Set;

/**
 * @author 应卓
 */
public interface RSA {

    @Documented
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public @interface DecryptByPrivateKey {

        public static final class FormatterFactory implements AnnotationFormatterFactory<DecryptByPrivateKey> {

            @Override
            public Set<Class<?>> getFieldTypes() {
                return Collections.singleton(String.class);
            }

            @Override
            public Printer<?> getPrinter(DecryptByPrivateKey decryptByPrivateKey, Class<?> aClass) {
                return NopStringFormatter.INSTANCE;
            }

            @Override
            public Parser<?> getParser(DecryptByPrivateKey decryptByPrivateKey, Class<?> aClass) {
                return (Parser<String>) (s, locale) -> RSAUtils.decryptByPrivateKey(s, SpringUtils.getBean(SecretAutoConfig.RSAProps.class).getPrivateKey());
            }
        }
    }

    @Documented
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public @interface DecryptByPublicKey {

        public static final class FormatterFactory implements AnnotationFormatterFactory<DecryptByPublicKey> {

            @Override
            public Set<Class<?>> getFieldTypes() {
                return Collections.singleton(String.class);
            }

            @Override
            public Printer<?> getPrinter(DecryptByPublicKey decryptByPublicKey, Class<?> aClass) {
                return NopStringFormatter.INSTANCE;
            }

            @Override
            public Parser<?> getParser(DecryptByPublicKey decryptByPublicKey, Class<?> aClass) {
                return (Parser<String>) (s, locale) -> RSAUtils.decryptByPublicKey(s, SpringUtils.getBean(SecretAutoConfig.RSAProps.class).getPublicKey());
            }
        }
    }

    @Documented
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public @interface EncryptByPublicKey {

        public static final class FormatterFactory implements AnnotationFormatterFactory<EncryptByPublicKey> {

            @Override
            public Set<Class<?>> getFieldTypes() {
                return Collections.singleton(String.class);
            }

            @Override
            public Printer<?> getPrinter(EncryptByPublicKey encryptByPublicKey, Class<?> aClass) {
                return NopStringFormatter.INSTANCE;
            }

            @Override
            public Parser<?> getParser(EncryptByPublicKey encryptByPublicKey, Class<?> aClass) {
                return (Parser<String>) (s, locale) -> RSAUtils.encryptByPublicKey(s, SpringUtils.getBean(SecretAutoConfig.RSAProps.class).getPublicKey());
            }
        }
    }

    @Documented
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public @interface EncryptByPrivateKey {

        public static final class FormatterFactory implements AnnotationFormatterFactory<EncryptByPrivateKey> {

            @Override
            public Set<Class<?>> getFieldTypes() {
                return Collections.singleton(String.class);
            }

            @Override
            public Printer<?> getPrinter(EncryptByPrivateKey encryptByPublicKey, Class<?> aClass) {
                return NopStringFormatter.INSTANCE;
            }

            @Override
            public Parser<?> getParser(EncryptByPrivateKey encryptByPublicKey, Class<?> aClass) {
                return (Parser<String>) (s, locale) -> RSAUtils.encryptByPrivateKey(s, SpringUtils.getBean(SecretAutoConfig.RSAProps.class).getPrivateKey());
            }
        }
    }

}
