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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
            types.add(Calendar.class);
            types.add(LocalDate.class);
            types.add(LocalDateTime.class);
            types.add(LocalTime.class);
            types.add(ZonedDateTime.class);
            types.add(YearMonth.class);
            types.add(MonthDay.class);
            FIELD_TYPES = Collections.unmodifiableSet(types);
        }

        @Override
        public Set<Class<?>> getFieldTypes() {
            return FIELD_TYPES;
        }

        @Override
        public Printer<?> getPrinter(DateTimeFormat annotation, Class<?> fieldType) {
            return (date, locale) -> date.toString();
        }

        @Override
        public Parser<?> getParser(DateTimeFormat annotation, Class<?> fieldType) {
            val patterns = annotation.patterns();

            return (text, locale) -> {
                if (ClassUtils.isAssignable(fieldType, Date.class)) {
                    return DateUtils.parseDate(text, annotation.patterns());
                } else if (ClassUtils.isAssignable(fieldType, Calendar.class)) {
                    return DateUtils.toCalendar(DateUtils.parseDate(text, annotation.patterns()));
                } else if (ClassUtils.isAssignable(fieldType, LocalDateTime.class)) {
                    return parseLocalDateTime(patterns, text);
                } else if (ClassUtils.isAssignable(fieldType, LocalDate.class)) {
                    return parseLocalDate(patterns, text);
                } else if (ClassUtils.isAssignable(fieldType, LocalTime.class)) {
                    return parseLocalTime(patterns, text);
                } else if (ClassUtils.isAssignable(fieldType, ZonedDateTime.class)) {
                    return parseZonedDateTime(patterns, text);
                } else if (ClassUtils.isAssignable(fieldType, YearMonth.class)) {
                    return parseYearMonth(patterns, text);
                } else {
                    return parseMonthDay(patterns, text);
                }
            };
        }

        private LocalDateTime parseLocalDateTime(String[] patterns, String text) {
            LocalDateTime date = null;
            for (String pattern : patterns) {
                try {
                    date = LocalDateTime.parse(text, DateTimeFormatter.ofPattern(pattern));
                } catch (DateTimeParseException ignored) {
                    //
                }
            }

            if (date != null) {
                return date;
            } else {
                throw new DateTimeParseException("Cannot parse", text, 0);
            }
        }

        private LocalDate parseLocalDate(String[] patterns, String text) {
            LocalDate date = null;
            for (String pattern : patterns) {
                try {
                    date = LocalDate.parse(text, DateTimeFormatter.ofPattern(pattern));
                } catch (DateTimeParseException ignored) {
                    //
                }
            }

            if (date != null) {
                return date;
            } else {
                throw new DateTimeParseException("Cannot parse", text, 0);
            }
        }

        private LocalTime parseLocalTime(String[] patterns, String text) {
            LocalTime date = null;
            for (String pattern : patterns) {
                try {
                    date = LocalTime.parse(text, DateTimeFormatter.ofPattern(pattern));
                } catch (DateTimeParseException ignored) {
                    //
                }
            }

            if (date != null) {
                return date;
            } else {
                throw new DateTimeParseException("Cannot parse", text, 0);
            }
        }

        private YearMonth parseYearMonth(String[] patterns, String text) {
            YearMonth date = null;
            for (String pattern : patterns) {
                try {
                    date = YearMonth.parse(text, DateTimeFormatter.ofPattern(pattern));
                } catch (DateTimeParseException ignored) {
                    //
                }
            }

            if (date != null) {
                return date;
            } else {
                throw new DateTimeParseException("Cannot parse", text, 0);
            }
        }

        private MonthDay parseMonthDay(String[] patterns, String text) {
            MonthDay date = null;
            for (String pattern : patterns) {
                try {
                    date = MonthDay.parse(text, DateTimeFormatter.ofPattern(pattern));
                } catch (DateTimeParseException ignored) {
                    //
                }
            }

            if (date != null) {
                return date;
            } else {
                throw new DateTimeParseException("Cannot parse", text, 0);
            }
        }

        private ZonedDateTime parseZonedDateTime(String[] patterns, String text) {
            ZonedDateTime date = null;
            for (String pattern : patterns) {
                try {
                    date = ZonedDateTime.parse(text, DateTimeFormatter.ofPattern(pattern));
                } catch (DateTimeParseException ignored) {
                    //
                }
            }

            if (date != null) {
                return date;
            } else {
                throw new DateTimeParseException("Cannot parse", text, 0);
            }
        }

    }

}
