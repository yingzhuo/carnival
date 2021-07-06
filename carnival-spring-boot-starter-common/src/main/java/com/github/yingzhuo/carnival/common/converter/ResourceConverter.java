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

import com.github.yingzhuo.carnival.common.io.ResourceOptional;
import com.github.yingzhuo.carnival.common.io.ResourceText;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 应卓
 * @since 1.9.9
 */
@SuppressWarnings("NullableProblems")
public class ResourceConverter implements GenericConverter {

    private static final Set<ConvertiblePair> CONVERTIBLE_PAIRS;

    static {
        final Set<ConvertiblePair> set = new HashSet<>();
        set.add(new ConvertiblePair(CharSequence.class, ResourceText.class));
        set.add(new ConvertiblePair(CharSequence.class, ResourceOptional.class));
        CONVERTIBLE_PAIRS = Collections.unmodifiableSet(set);
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return CONVERTIBLE_PAIRS;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source == null) return null;

        Class<?> clz = targetType.getObjectType();
        if (clz == ResourceText.class) {
            return ResourceText.of(source.toString());
        }

        if (clz == ResourceOptional.class) {
            return ResourceOptional.of(source.toString());
        }

        throw new AssertionError(); // 不可能运行到此处
    }

}
