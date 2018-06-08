/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.datamodel.account;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * @author 应卓
 */
public class GenderFormatter implements Formatter<Gender> {

    @Override
    public Gender parse(String text, Locale locale) throws ParseException {
        if ("-1".equals(text) || "未知".equals(text) || "UNKOWN".equalsIgnoreCase(text)) return Gender.UNKOWN;
        if ("0".equals(text) || "女".equals(text) || "FEMALE".equalsIgnoreCase(text)) return Gender.FEMALE;
        if ("1".equals(text) || "男".equals(text) || "MALE".equalsIgnoreCase(text)) return Gender.MALE;
        throw new ParseException("Cannot parse Gender from '" + text + "'.", 0);
    }

    @Override
    public String print(Gender object, Locale locale) {
        return object == null ? "null" : object.getDisplayName();
    }

}
