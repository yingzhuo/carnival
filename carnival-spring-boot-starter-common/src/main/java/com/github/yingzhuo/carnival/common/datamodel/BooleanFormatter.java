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

import java.text.ParseException;
import java.util.Locale;

/**
 * @author 应卓
 */
public class BooleanFormatter extends AbstractObjectFormatter<Boolean> {

    @Override
    public Boolean parse(String text, Locale locale) throws ParseException {

        if ("true".equalsIgnoreCase(text) || "1".equals(text) || "yes".equalsIgnoreCase(text) || "y".equalsIgnoreCase(text)) {
            return Boolean.TRUE;
        }

        if ("false".equalsIgnoreCase(text) || "0".equals(text) || "no".equalsIgnoreCase(text) || "n".equalsIgnoreCase(text)) {
            return Boolean.FALSE;
        }

        throw new ParseException("invalid format", 0);
    }

}
