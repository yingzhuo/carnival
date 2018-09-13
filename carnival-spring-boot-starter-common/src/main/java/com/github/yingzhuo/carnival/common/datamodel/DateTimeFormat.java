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

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.lang.annotation.*;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

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
        @Override
        public Set<Class<?>> getFieldTypes() {
            return Collections.singleton(Date.class);
        }

        @Override
        public Printer<?> getPrinter(DateTimeFormat annotation, Class<?> fieldType) {
            return (Printer<Date>) (date, locale) -> date == null ? "null" : date.toString();
        }

        @Override
        public Parser<?> getParser(DateTimeFormat annotation, Class<?> fieldType) {
            return (Parser<Date>) (text, locale) -> DateUtils.parseDate(text, annotation.patterns());
        }
    }

}
