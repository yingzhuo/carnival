/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.datamodel;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.lang.annotation.*;
import java.util.Collections;
import java.util.Set;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface TrimFormat {

    public static class FormatterFactory implements AnnotationFormatterFactory<TrimFormat> {

        @Override
        public Set<Class<?>> getFieldTypes() {
            return Collections.singleton(String.class);
        }

        @Override
        public Printer<?> getPrinter(TrimFormat annotation, Class<?> fieldType) {
            return (Printer<String>) (object, locale) -> object;
        }

        @Override
        public Parser<?> getParser(TrimFormat annotation, Class<?> fieldType) {
            return (Parser<String>) (text, locale) -> text.trim();
        }
    }

}
