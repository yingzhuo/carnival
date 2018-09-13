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
import java.util.HashSet;
import java.util.Set;

/**
 * @author 应卓
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface LongCurrencyFormat {

    public int value() default 2;

    public static final class FormatterFactory implements AnnotationFormatterFactory<LongCurrencyFormat> {

        @Override
        public Set<Class<?>> getFieldTypes() {
            Set<Class<?>> set = new HashSet<>();
            set.add(long.class);
            set.add(Long.class);
            return set;
        }

        @Override
        public Printer<?> getPrinter(LongCurrencyFormat annotation, Class<?> fieldType) {
            return (Printer<Long>) (object, locale) -> object != null ? object.toString() : "null";
        }

        @Override
        public Parser<?> getParser(LongCurrencyFormat annotation, Class<?> fieldType) {
            final int v = annotation.value();
            return (Parser<Long>) (text, locale) -> (long) (Double.valueOf(text) * (int) Math.pow(10D, (double) v));
        }
    }

}
