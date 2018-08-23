/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.sentinel.exception;

/**
 * @author 应卓
 */
public class AccessDeniedException extends RuntimeException {

    private static final long serialVersionUID = 4254806770771524411L;

    public AccessDeniedException() {
        this(null);
    }

    public AccessDeniedException(String message) {
        super(message);
    }

}
