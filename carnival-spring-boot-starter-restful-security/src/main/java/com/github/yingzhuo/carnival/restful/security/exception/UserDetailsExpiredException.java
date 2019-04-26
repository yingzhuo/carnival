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
 * 账号已过期异常
 *
 * @author 应卓
 * @see RestfulSecurityException
 * @see AuthenticationException
 */
public class UserDetailsExpiredException extends AuthenticationException {

    public UserDetailsExpiredException() {
    }

    public UserDetailsExpiredException(String message) {
        super(message);
    }
}
