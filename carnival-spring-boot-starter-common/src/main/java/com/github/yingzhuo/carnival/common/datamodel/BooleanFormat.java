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

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class BooleanFormat implements Formatter<Boolean> {

    @Override
    public Boolean parse(String text, Locale locale) throws ParseException {

        if ("true".equalsIgnoreCase(text) || "1".equalsIgnoreCase(text) || "yes".equalsIgnoreCase(text) || "y".equalsIgnoreCase(text)) {
            return Boolean.TRUE;
        }

        if ("false".equalsIgnoreCase(text) || "0".equalsIgnoreCase(text) || "no".equalsIgnoreCase(text) || "n".equalsIgnoreCase(text)) {
            return Boolean.FALSE;
        }

        throw new ParseException("Cannot parse boolean from '" + text + "'", 0);
    }

    @Override
    public String print(Boolean object, Locale locale) {
        return object == null ? "null" : object.toString();
    }

}
