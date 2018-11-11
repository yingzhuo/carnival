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

/**
 * @author 应卓
 */
public final class CaptchaId implements Serializable {

    private static final long serialVersionUID = -4490018081556442337L;
    private final String id;

    private CaptchaId(String id) {
        this.id = Objects.requireNonNull(id);
    }

    public static CaptchaId of(String id) {
        return new CaptchaId(Objects.requireNonNull(id));
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaptchaId captchaId = (CaptchaId) o;
        return Objects.equals(id, captchaId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
