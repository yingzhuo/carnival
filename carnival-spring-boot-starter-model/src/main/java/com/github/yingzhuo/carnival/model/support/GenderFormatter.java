/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.model.support;

import com.github.yingzhuo.carnival.model.Gender;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author 应卓
 */
public class GenderFormatter implements Formatter<Gender> {

    @Override
    public Gender parse(String text, Locale locale) throws ParseException {
        if (text == null) return null;

        if ("UNKNOWN".equalsIgnoreCase(text) || "-1".equalsIgnoreCase(text)) {
            return Gender.UNKNOWN;
        }

        if ("FEMALE".equalsIgnoreCase(text) || "0".equalsIgnoreCase(text)) {
            return Gender.FEMALE;
        }

        if ("MALE".equalsIgnoreCase(text) || "1".equalsIgnoreCase(text)) {
            return Gender.MALE;
        }

        throw new ParseException("For input: " + text, 0);
    }

    @Override
    public String print(Gender gender, Locale locale) {
        return gender.name();
    }

}
