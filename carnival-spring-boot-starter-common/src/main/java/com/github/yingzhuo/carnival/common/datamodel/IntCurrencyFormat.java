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
public @interface IntCurrencyFormat {

    public int value() default 2;

    public static final class FormatterFactory implements AnnotationFormatterFactory<IntCurrencyFormat> {

        @Override
        public Set<Class<?>> getFieldTypes() {
            Set<Class<?>> set = new HashSet<>();
            set.add(int.class);
            set.add(Integer.class);
            return set;
        }

        @Override
        public Printer<?> getPrinter(IntCurrencyFormat annotation, Class<?> fieldType) {
            return (Printer<Integer>) (object, locale) -> object.toString();
        }

        @Override
        public Parser<?> getParser(IntCurrencyFormat annotation, Class<?> fieldType) {
            final int v = annotation.value();
            return (Parser<Integer>) (text, locale) -> (int) (Double.parseDouble(text) * (int) Math.pow(10D, (double) v));
        }
    }

}
