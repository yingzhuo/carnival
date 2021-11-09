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
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.format.support.DefaultFormattingConversionService;

/**
 * @author 应卓
 * @see SpringUtils
 * @see org.springframework.core.convert.ConversionService
 */
public final class ConversionUtils {

    private static final ConversionService DEFAULT = new DefaultFormattingConversionService();

    private ConversionUtils() {
    }

    public static ConversionService getConversionService() {
        try {
            return SpringUtils.getBean(ConversionService.class);
        } catch (Exception e) {
            return DEFAULT;
        }
    }

    public static boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        val service = getConversionService();
        return service.canConvert(sourceType, targetType);
    }

    public static <T> T convert(Object source, Class<T> targetType) {
        val service = getConversionService();
        return service.convert(source, targetType);
    }

    public static Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        val service = getConversionService();
        return service.convert(source, sourceType, targetType);
    }

}
