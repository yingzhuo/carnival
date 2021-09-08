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

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.text.ParseException;
import java.util.*;

/**
 * @author 应卓
 * @since 1.9.9
 */
@SuppressWarnings("NullableProblems")
public class DateTimeConverter implements GenericConverter {

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
        set.add(new ConvertiblePair(CharSequence.class, Date.class));
        set.add(new ConvertiblePair(CharSequence.class, Calendar.class));
        CONVERTIBLE_PAIRS = Collections.unmodifiableSet(set);
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return CONVERTIBLE_PAIRS;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        try {
            final String text = source.toString();
            final Class<?> clz = targetType.getObjectType();
            final Date date = DateUtils.parseDateStrictly(text, PATTERNS);

            if (clz == Date.class) return date;
            if (clz == Calendar.class) return DateUtils.toCalendar(date);

            throw new AssertionError();
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

}
