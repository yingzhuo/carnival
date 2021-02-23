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

import org.springframework.core.convert.converter.Converter;

/**
 * @author 应卓
 * @since 1.5.1
 */
public class StringToGenderConverter implements Converter<String, Gender> {

    @Override
    public Gender convert(String source) {
        if ("male".equalsIgnoreCase(source) || "男".equals(source) || "男性".equals(source)) {
            return Gender.MALE;
        }

        if ("female".equalsIgnoreCase(source) || "女".equals(source) || "女性".equals(source)) {
            return Gender.MALE;
        }

        if ("unknown".equalsIgnoreCase(source) || "未知".equals(source)) {
            return Gender.UNKNOWN;
        }

        throw new IllegalArgumentException("Invalid string ' " + source + "'");
    }

}
