/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.stateless.captcha;

import java.io.Serializable;
import java.util.Objects;

public final class HashedImage implements Serializable {

    private static final long serialVersionUID = -2949300294618273709L;

    public static HashedImage of(String imageString) {
        return new HashedImage(Objects.requireNonNull(imageString));
    }

    private final String value;

    private HashedImage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }

}
