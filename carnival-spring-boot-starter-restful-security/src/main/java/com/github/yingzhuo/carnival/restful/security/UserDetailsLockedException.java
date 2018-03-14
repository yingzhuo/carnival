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

/**
 * 账号被锁定异常
 *
 * @author 应卓
 * @see com.github.yingzhuo.carnival.restful.security.impl.CheckUtils
 * @see RestfulSecurityException
 * @see AuthenticationException
 */
public class UserDetailsLockedException extends AuthenticationException {

    private static final long serialVersionUID = 3640426498330580934L;

    public UserDetailsLockedException() {
        this(null);
    }

    public UserDetailsLockedException(String message) {
        super(message);
    }
}
