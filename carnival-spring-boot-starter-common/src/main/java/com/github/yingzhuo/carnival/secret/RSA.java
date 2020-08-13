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

import com.github.yingzhuo.carnival.secret.rsa.RSAHelper;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.lang.annotation.*;
import java.util.Collections;
import java.util.Set;

/**
 * @author 应卓
 */
@Deprecated
public interface RSA {

    @Documented
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public @interface DecryptingByPrivateKey {

        public static final class FormatterFactory implements AnnotationFormatterFactory<DecryptingByPrivateKey> {
            private final RSAHelper helper;

            public FormatterFactory(RSAHelper helper) {
                this.helper = helper;
            }

            @Override
            public Set<Class<?>> getFieldTypes() {
                return Collections.singleton(String.class);
            }

            @Override
            public Printer<?> getPrinter(DecryptingByPrivateKey decryptByPrivateKey, Class<?> aClass) {
                return NopStringFormatter.INSTANCE;
            }

            @Override
            public Parser<?> getParser(DecryptingByPrivateKey decryptByPrivateKey, Class<?> aClass) {
                return (Parser<String>) (text, locale) -> helper.decryptByPrivateKey(text);
            }
        }
    }

    @Documented
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public @interface DecryptingByPublicKey {

        public static final class FormatterFactory implements AnnotationFormatterFactory<DecryptingByPublicKey> {
            private final RSAHelper helper;

            public FormatterFactory(RSAHelper helper) {
                this.helper = helper;
            }

            @Override
            public Set<Class<?>> getFieldTypes() {
                return Collections.singleton(String.class);
            }

            @Override
            public Printer<?> getPrinter(DecryptingByPublicKey decryptByPublicKey, Class<?> aClass) {
                return NopStringFormatter.INSTANCE;
            }

            @Override
            public Parser<?> getParser(DecryptingByPublicKey decryptByPublicKey, Class<?> aClass) {
                return (Parser<String>) (text, locale) -> helper.decryptByPublicKey(text);
            }
        }
    }

    @Documented
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public @interface EncryptingByPublicKey {

        public static final class FormatterFactory implements AnnotationFormatterFactory<EncryptingByPublicKey> {
            private final RSAHelper helper;

            public FormatterFactory(RSAHelper helper) {
                this.helper = helper;
            }

            @Override
            public Set<Class<?>> getFieldTypes() {
                return Collections.singleton(String.class);
            }

            @Override
            public Printer<?> getPrinter(EncryptingByPublicKey encryptByPublicKey, Class<?> aClass) {
                return NopStringFormatter.INSTANCE;
            }

            @Override
            public Parser<?> getParser(EncryptingByPublicKey encryptByPublicKey, Class<?> aClass) {
                return (Parser<String>) (text, locale) -> helper.encryptByPublicKey(text);
            }
        }
    }

    @Documented
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public @interface EncryptingByPrivateKey {

        public static final class FormatterFactory implements AnnotationFormatterFactory<EncryptingByPrivateKey> {
            private final RSAHelper helper;

            public FormatterFactory(RSAHelper helper) {
                this.helper = helper;
            }

            @Override
            public Set<Class<?>> getFieldTypes() {
                return Collections.singleton(String.class);
            }

            @Override
            public Printer<?> getPrinter(EncryptingByPrivateKey encryptByPublicKey, Class<?> aClass) {
                return NopStringFormatter.INSTANCE;
            }

            @Override
            public Parser<?> getParser(EncryptingByPrivateKey encryptByPublicKey, Class<?> aClass) {
                return (Parser<String>) (text, locale) -> helper.encryptByPrivateKey(text);
            }
        }
    }

}
