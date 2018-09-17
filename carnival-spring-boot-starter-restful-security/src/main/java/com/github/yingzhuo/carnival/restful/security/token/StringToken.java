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

import java.io.Serializable;
import java.util.Objects;

/**
 * @author 应卓
 */
public class StringToken implements Token, Serializable {

    private static final long serialVersionUID = 5201948233147060904L;
    private final String value;

    public StringToken(String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static StringToken of(String value) {
        return new StringToken(value);
    }

    public final String getValue() {
        return value;
    }

    public final int length() {
        return value.length();
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringToken that = (StringToken) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
