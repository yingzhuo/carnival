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
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface StandardPhoneNumber {

    public static class StandardPhoneNumberFormatterFactory implements AnnotationFormatterFactory<StandardPhoneNumber> {

        @Override
        public Set<Class<?>> getFieldTypes() {
            return Collections.singleton(String.class);
        }

        @Override
        public Printer<?> getPrinter(StandardPhoneNumber annotation, Class<?> fieldType) {
            return (Printer<String>) (object, locale) -> object;
        }

        @Override
        public Parser<?> getParser(StandardPhoneNumber annotation, Class<?> fieldType) {
            return (Parser<String>) (t, locale) -> {
                t = StringUtils.deleteAny(t, "-+");

                if (t.length() > 11) {
                    t = t.substring(t.length() - 11);
                }

                return t;
            };
        }
    }
}
