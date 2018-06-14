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

import com.github.yingzhuo.carnival.common.DisplayNamed;
import com.github.yingzhuo.carnival.common.IntCoded;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * 性别
 *
 * @author 应卓
 */
public enum Gender implements IntCoded, DisplayNamed {

    UNKNOWN(-1, "未知"),
    FEMALE(0, "女"),
    MALE(1, "男");

    private int code;
    private String displayName;

    private Gender(int code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public String toString() {
        return getDisplayName();
    }

    public static class GenderFormatter implements Formatter<Gender> {
        @Override
        public Gender parse(String text, Locale locale) throws ParseException {
            if ("-1".equals(text) || "未知".equals(text) || "UNKNOWN".equalsIgnoreCase(text) || "U".equalsIgnoreCase(text)) return Gender.UNKNOWN;
            if ("0".equals(text) || "女".equals(text) || "FEMALE".equalsIgnoreCase(text) || "F".equalsIgnoreCase(text)) return Gender.FEMALE;
            if ("1".equals(text) || "男".equals(text) || "MALE".equalsIgnoreCase(text) || "M".equalsIgnoreCase(text)) return Gender.MALE;
            throw new ParseException("Cannot parse Gender from '" + text + "'.", 0);
        }

        @Override
        public String print(Gender object, Locale locale) {
            return object == null ? "null" : object.getDisplayName();
        }
    }
}
