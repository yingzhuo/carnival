/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.token;

import java.util.Objects;

/**
 * @author 应卓
 */
public class StringToken implements Token {

    private static final long serialVersionUID = 5201948233147060904L;
    private final String value;

    public static StringToken of(String value) {
        return new StringToken(value);
    }

    public StringToken(String value) {
        this.value = Objects.requireNonNull(value);
    }

    public String getValue() {
        return value;
    }

    public int length() {
        return value.length();
    }

    @Override
    public String toString() {
        return getValue();
    }

}
