/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.support;

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
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface IntPrice {

    public static final class IntPriceAnnotation implements AnnotationFormatterFactory<IntPrice> {

        @Override
        public Set<Class<?>> getFieldTypes() {
            Set<Class<?>> set = new HashSet<>();
            set.add(int.class);
            set.add(Integer.class);
            return set;
        }

        @Override
        public Printer<?> getPrinter(IntPrice annotation, Class<?> fieldType) {
            return (Printer<Integer>) (object, locale) -> object != null ? object.toString() : "null";
        }

        @Override
        public Parser<?> getParser(IntPrice annotation, Class<?> fieldType) {
            return (Parser<Integer>) (text, locale) -> (int) (Double.valueOf(text) * 10000 / 100);
        }
    }

}
