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

import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.*;

/**
 * @author 应卓
 * @since 1.9.9
 */
@SuppressWarnings("NullableProblems")
public class DateTimeConverter implements GenericConverter {

    private static final Set<ConvertiblePair> CONVERTIBLE_PAIRS;

    static {
        final Set<ConvertiblePair> set = new HashSet<>();
        set.add(new ConvertiblePair(CharSequence.class, Date.class));
        set.add(new ConvertiblePair(CharSequence.class, Calendar.class));
        CONVERTIBLE_PAIRS = Collections.unmodifiableSet(set);
    }

    public static final String[] PATTERN = {
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

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return CONVERTIBLE_PAIRS;
    }

    @Override
    @SneakyThrows
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) return null;

        final String text = source.toString();
        final Class<?> clz = targetType.getObjectType();
        final Date date = DateUtils.parseDateStrictly(text, PATTERN);

        if (clz == Date.class) return date;
        if (clz == Calendar.class) return DateUtils.toCalendar(date);

        throw new AssertionError(); // 不可能运行到此处
    }

}
