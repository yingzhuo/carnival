/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.exception.business;

/**
 * @author 应卓
 * @since 1.6.15
 */
public final class NoSuchCodeException extends IllegalArgumentException {

    private static String getMessage(String code) {
        if (code == null) {
            return "Code should NOT be null.";
        } else {
            return "Illegal code '" + code + "' for creating BusinessException.";
        }
    }

    public final String code;

    public NoSuchCodeException(String code) {
        super(getMessage(code));
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
