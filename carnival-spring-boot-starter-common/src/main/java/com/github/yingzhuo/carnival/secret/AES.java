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
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.lang.annotation.*;
import java.util.Collections;
import java.util.Set;

/**
 * @author 应卓
 * @since 1.6.2
 */
public interface AES {

    @Documented
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public @interface Encrypting {

        public static final class FormatterFactory implements AnnotationFormatterFactory<Encrypting> {

            private final String passphrase;

            public FormatterFactory(String passphrase) {
                this.passphrase = passphrase;
            }

            @Override
            public Set<Class<?>> getFieldTypes() {
                return Collections.singleton(String.class);
            }

            @Override
            public Printer<?> getPrinter(Encrypting annotation, Class<?> fieldType) {
                return NopStringFormatter.INSTANCE;
            }

            @Override
            public Parser<?> getParser(Encrypting annotation, Class<?> fieldType) {
                return (Parser<String>) (s, locale) -> AESUtils.encrypt(s, passphrase);
            }
        }
    }

    @Documented
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public @interface Decrypting {

        public static final class FormatterFactory implements AnnotationFormatterFactory<Decrypting> {

            private final String passphrase;

            public FormatterFactory(String passphrase) {
                this.passphrase = passphrase;
            }

            @Override
            public Set<Class<?>> getFieldTypes() {
                return Collections.singleton(String.class);
            }

            @Override
            public Printer<?> getPrinter(Decrypting annotation, Class<?> fieldType) {
                return NopStringFormatter.INSTANCE;
            }

            @Override
            public Parser<?> getParser(Decrypting annotation, Class<?> fieldType) {
                return (Parser<String>) (s, locale) -> AESUtils.decrypt(s, passphrase);
            }
        }
    }

}
