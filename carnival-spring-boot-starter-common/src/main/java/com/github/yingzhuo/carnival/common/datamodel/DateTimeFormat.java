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

import lombok.val;
import org.apache.commons.lang3.ClassUtils;
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
            "dd/MM/yyyy"
    };

    public static class FormatterFactory implements AnnotationFormatterFactory<DateTimeFormat> {

        private static final Set<Class<?>> FIELD_TYPES;

        static {
            val types = new HashSet<Class<?>>();
            types.add(Date.class);
            types.add(Calendar.class);
            FIELD_TYPES = Collections.unmodifiableSet(types);
        }

        @Override
        public Set<Class<?>> getFieldTypes() {
            return FIELD_TYPES;
        }

        @Override
        public Printer<?> getPrinter(DateTimeFormat annotation, Class<?> fieldType) {
            return (date, locale) -> date == null ? "null" : date.toString();
        }

        @Override
        public Parser<?> getParser(DateTimeFormat annotation, Class<?> fieldType) {
            return (text, locale) -> {
                val date = DateUtils.parseDate(text, annotation.patterns());

                if (ClassUtils.isAssignable(fieldType, Date.class)) {
                    return date;
                } else {
                    return DateUtils.toCalendar(date);
                }
            };
        }
    }

}
