/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security;

import java.io.Serializable;

/**
 * @author 应卓
 * @since 1.6.5
 */
public final class SkipReason implements Serializable {

    private final boolean annotatedAsSkip;
    private final TokenAbsent tokenAbsent;

    public SkipReason(boolean annotatedAsSkip, TokenAbsent tokenAbsent) {
        this.annotatedAsSkip = annotatedAsSkip;
        this.tokenAbsent = tokenAbsent;
    }

    public boolean isAnnotatedAsSkip() {
        return annotatedAsSkip;
    }

    public TokenAbsent getTokenAbsent() {
        return tokenAbsent;
    }

    // ---------------------------------------------------------------------------------------------------------------

    public enum TokenAbsent {
        YES, NO, UNKNOWN
    }

}
