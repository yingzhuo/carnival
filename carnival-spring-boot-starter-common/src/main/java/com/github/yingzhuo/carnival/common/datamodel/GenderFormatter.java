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
import org.springframework.format.Formatter;

import java.util.Locale;

/**
 * @author 应卓
 * @since 1.3.2
 */
public class GenderFormatter implements Formatter<Gender>, Converter<String, Gender> {

    @Override
    public Gender parse(String text, Locale locale) {

        if ("male".equalsIgnoreCase(text) || "男".equalsIgnoreCase(text) || "男性".equalsIgnoreCase(text)) {
            return Gender.MALE;
        }

        if ("female".equalsIgnoreCase(text) || "女".equalsIgnoreCase(text) || "女性".equalsIgnoreCase(text)) {
            return Gender.MALE;
        }

        return Gender.UNKNOWN;
    }

    @Override
    public String print(Gender object, Locale locale) {
        return object.toString();
    }

    @Override
    public Gender convert(String source) {
        return parse(source, Locale.getDefault());
    }

}
