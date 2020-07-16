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

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.lang.annotation.*;
import java.util.*;

/**
 * @author 应卓
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface DateTimeFormat {

    public String[] patterns() default {
//            "EEE MMM dd HH:mm:ss zzz yyyy",
            "yyyy-MM-dd",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm:ss.SSS",
            "yyyy-MM-dd'T'HH:mm:ss",
            "yyyy-MM-dd'T'HH:mm:ss.SSS",
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
            "yyyy-MM-dd'T'HH:mm:ss.SSS Z",
            "yyyyMMdd",
            "yyMMddHHmmss",
            "yyMMddHHmmssSSS",
            "dd/MM/yy",
            "dd/MM/yyyy",
            "yyyy/MM/dd",
            "yyyy/MM/dd HH:mm:ss",
            "yyyy/MM/dd HH:mm:ss.SSS",
            "yyyy-MM",
            "MM-dd"
    };

    public static class FormatterFactory implements AnnotationFormatterFactory<DateTimeFormat> {

        private static final Set<Class<?>> FIELD_TYPES;

        static {
            final Set<Class<?>> types = new HashSet<>();
            types.add(Date.class);
            FIELD_TYPES = Collections.unmodifiableSet(types);
        }

        @Override
        public Set<Class<?>> getFieldTypes() {
            return FIELD_TYPES;
        }

        @Override
        public Printer<?> getPrinter(DateTimeFormat annotation, Class<?> fieldType) {
            return new Printer<Date>() {
                @Override
                public String print(Date object, Locale locale) {
                    return DateFormatUtils.format(object, "yyyy-MM-dd'T'HH:mm:ss.SSS");
                }
            };
        }

        @Override
        public Parser<?> getParser(DateTimeFormat annotation, Class<?> fieldType) {
            return (text, locale) -> DateUtils.parseDate(text, annotation.patterns());
        }
    }

}
