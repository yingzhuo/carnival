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

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 应卓
 * @since 1.9.9
 */
@SuppressWarnings("NullableProblems")
public class XTimeConverter implements GenericConverter {

    public static final String[] PATTERNS = {
            "yyyy-MM-dd",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm:ss.SSS",
            "yyyy-MM-dd'T'HH:mm:ss",
            "yyyy-MM-dd'T'HH:mm:ss.SSS",
            "yyyyMMdd",
            "yyMMddHHmmss",
            "yyMMddHHmmssSSS",
            "yyyy/MM/dd",
            "yyyy/MM/dd HH:mm:ss",
            "yyyy/MM/dd HH:mm:ss.SSS"
    };

    private static final Set<ConvertiblePair> CONVERTIBLE_PAIRS;

    static {
        final Set<ConvertiblePair> set = new HashSet<>();
        set.add(new ConvertiblePair(CharSequence.class, LocalDate.class));
        set.add(new ConvertiblePair(CharSequence.class, LocalDateTime.class));
        set.add(new ConvertiblePair(CharSequence.class, Year.class));
        set.add(new ConvertiblePair(CharSequence.class, YearMonth.class));
        set.add(new ConvertiblePair(CharSequence.class, MonthDay.class));
        CONVERTIBLE_PAIRS = Collections.unmodifiableSet(set);
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return CONVERTIBLE_PAIRS;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) return null;

        final String text = source.toString();
        final Class<?> clz = targetType.getObjectType();

        if (clz == LocalDate.class) {
            return parseLocalDate(text, PATTERNS);
        }

        if (clz == LocalDateTime.class) {
            return parseLocalDateTime(text, PATTERNS);
        }

        if (clz == Year.class) {
            return Year.parse(text, DateTimeFormatter.ofPattern("yyyy"));
        }

        if (clz == YearMonth.class) {
            return YearMonth.parse(text, DateTimeFormatter.ofPattern("yyyy-MM"));
        }

        if (clz == MonthDay.class) {
            return MonthDay.parse(text, DateTimeFormatter.ofPattern("MM-dd"));
        }

        return null;
    }

    private LocalDate parseLocalDate(String text, String... patterns) {
        LocalDate result;
        for (String pattern : patterns) {
            try {
                result = LocalDate.parse(text, DateTimeFormatter.ofPattern(pattern));
            } catch (Exception e) {
                continue;
            }
            if (result != null) {
                return result;
            }
        }
        throw new IllegalArgumentException();
    }

    private LocalDateTime parseLocalDateTime(String text, String... patterns) {
        LocalDateTime result;
        for (String pattern : patterns) {
            try {
                result = LocalDateTime.parse(text, DateTimeFormatter.ofPattern(pattern));
            } catch (Exception e) {
                continue;
            }
            if (result != null) {
                return result;
            }
        }
        throw new IllegalArgumentException();
    }

}
