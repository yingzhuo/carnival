/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.converter;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.lang.annotation.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 应卓
 * @since 1.9.9
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@SuppressWarnings("NullableProblems")
public @interface CurrencyFormat {

    public int value() default 2;

    public static final class FormatterFactory implements AnnotationFormatterFactory<CurrencyFormat> {

        @Override
        public Set<Class<?>> getFieldTypes() {
            Set<Class<?>> set = new HashSet<>();
            set.add(long.class);
            set.add(Long.class);
            return set;
        }

        @Override
        public Printer<?> getPrinter(CurrencyFormat annotation, Class<?> fieldType) {
            return (Printer<Long>) (object, locale) -> object.toString();
        }

        @Override
        public Parser<?> getParser(CurrencyFormat annotation, Class<?> fieldType) {
            final int v = annotation.value();
            return (Parser<Long>) (text, locale) -> (long) (Double.parseDouble(text) * (int) Math.pow(10D, v));
        }
    }

}
