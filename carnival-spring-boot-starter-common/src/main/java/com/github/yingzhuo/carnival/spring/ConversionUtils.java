/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.spring;

import lombok.val;
import org.springframework.core.convert.TypeDescriptor;

/**
 * @author 应卓
 */
public final class ConversionUtils {

    private ConversionUtils() {
        super();
    }

    public static <T> T convert(Object source, Class<T> targetType) {
        val service = SpringUtils.getConversionService();
        return service.convert(source, targetType);
    }

    public static Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        val service = SpringUtils.getConversionService();
        return service.convert(source, sourceType, targetType);
    }

}
