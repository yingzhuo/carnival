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

import com.github.yingzhuo.carnival.common.util.AtoiUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author 应卓
 * @since 1.3.2
 */
public class DoubleFormatter implements Formatter<Double>, Converter<String, Double> {

    @Override
    public Double parse(String text, Locale locale) throws ParseException {
        try {
            return AtoiUtils.toDouble(text);
        } catch (Exception e) {
            throw new ParseException("invalid format", 0);
        }
    }

    @Override
    public String print(Double object, Locale locale) {
        return object.toString();
    }

    @Override
    public Double convert(String source) {
        try {
            return parse(source, Locale.getDefault());
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

}
