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

import javax.servlet.http.HttpServletRequest;

/**
 * 账号被锁定异常
 *
 * @author 应卓
 * @see RestfulSecurityException
 */
public class UserDetailsLockedException extends AuthorizationException {

    public UserDetailsLockedException(HttpServletRequest request) {
        super(request);
    }

    public UserDetailsLockedException(String message, HttpServletRequest request) {
        super(message, request);
    }

}
