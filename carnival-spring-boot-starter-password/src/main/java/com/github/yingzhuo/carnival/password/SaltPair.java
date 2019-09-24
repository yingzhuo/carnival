/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.password;

import java.io.Serializable;
import java.util.Objects;

/**
 * (immutable)
 *
 * @author 应卓
 * @since 1.1.12
 */
public final class SaltPair implements Serializable {

    public final String left;
    public final String right;

    public SaltPair(String left, String right) {
        this.left = Objects.requireNonNull(left);
        this.right = Objects.requireNonNull(right);
    }

    @Override
    public String toString() {
        return String.format("%s:%s", left, right);
    }

}
