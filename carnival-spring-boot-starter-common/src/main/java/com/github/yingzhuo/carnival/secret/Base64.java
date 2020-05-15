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

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.lang.annotation.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Set;

/**
 * @author 应卓
 */
public interface Base64 {

    @Documented
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public @interface Encoding {

        public static final class FormatterFactory implements AnnotationFormatterFactory<Encoding> {
            @Override
            public Set<Class<?>> getFieldTypes() {
                return Collections.singleton(String.class);
            }

            @Override
            public Printer<?> getPrinter(Encoding annotation, Class<?> fieldType) {
                return NopStringFormatter.INSTANCE;
            }

            @Override
            public Parser<?> getParser(Encoding annotation, Class<?> fieldType) {
                return (Parser<String>) (s, locale) -> java.util.Base64.getEncoder().encodeToString(s.getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    @Documented
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public @interface Decoding {

        public static final class FormatterFactory implements AnnotationFormatterFactory<Decoding> {
            @Override
            public Set<Class<?>> getFieldTypes() {
                return Collections.singleton(String.class);
            }

            @Override
            public Printer<?> getPrinter(Decoding annotation, Class<?> fieldType) {
                return NopStringFormatter.INSTANCE;
            }

            @Override
            public Parser<?> getParser(Decoding annotation, Class<?> fieldType) {
                return (Parser<String>) (s, locale) -> new String(java.util.Base64.getUrlDecoder().decode(s.getBytes(StandardCharsets.UTF_8)));
            }
        }
    }

}
