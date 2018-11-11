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
import org.springframework.util.StringUtils;

import java.lang.annotation.*;
import java.util.Collections;
import java.util.Set;

/**
 * @author 应卓
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface PhoneNumberFormat {

    public String charsToDelete() default "-+/\\ \t";

    public int right() default 11;

    public static class FormatterFactory implements AnnotationFormatterFactory<PhoneNumberFormat> {

        @Override
        public Set<Class<?>> getFieldTypes() {
            return Collections.singleton(String.class);
        }

        @Override
        public Printer<?> getPrinter(PhoneNumberFormat annotation, Class<?> fieldType) {
            return (Printer<String>) (object, locale) -> object;
        }

        @Override
        public Parser<?> getParser(PhoneNumberFormat annotation, Class<?> fieldType) {
            return (Parser<String>) (t, locale) -> {
                t = StringUtils.deleteAny(t, annotation.charsToDelete());

                if (t.length() > annotation.right()) {
                    t = t.substring(t.length() - annotation.right());
                }

                return t;
            };
        }
    }
}
