/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.exception;

/**
 * 账号被锁定异常
 *
 * @author 应卓
 * @see RestfulSecurityException
 */
public class UserDetailsLockedException extends AuthorizationException {

    private static final long serialVersionUID = 3640426498330580934L;

    public UserDetailsLockedException() {
        super();
    }

    public UserDetailsLockedException(String message) {
        super(message);
    }

    public UserDetailsLockedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDetailsLockedException(Throwable cause) {
        super(cause);
    }
}
