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
        if ("male".equalsIgnoreCase(source) || "男".equalsIgnoreCase(source) || "男性".equalsIgnoreCase(source)) {
            return Gender.MALE;
        }

        if ("female".equalsIgnoreCase(source) || "女".equalsIgnoreCase(source) || "女性".equalsIgnoreCase(source)) {
            return Gender.MALE;
        }

        throw new IllegalArgumentException("Invalid string ' " + source + "'");
    }

}
