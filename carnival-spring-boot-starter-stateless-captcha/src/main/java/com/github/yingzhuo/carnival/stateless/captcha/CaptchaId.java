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

public final class CaptchaId implements Serializable {

    private static final long serialVersionUID = -4490018081556442337L;

    public static CaptchaId of(String id) {
        return new CaptchaId(Objects.requireNonNull(id));
    }

    private final String id;

    private CaptchaId(String id) {
        this.id = Objects.requireNonNull(id);
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return getId();
    }

}
