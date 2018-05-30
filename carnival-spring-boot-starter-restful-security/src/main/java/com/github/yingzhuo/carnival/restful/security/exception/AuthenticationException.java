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
 * 认证异常
 *
 * @author 应卓
 * @see com.github.yingzhuo.carnival.restful.security.impl.CheckUtils
 * @see RestfulSecurityException
 * @see UserDetailsExpiredException
 * @see UserDetailsLockedException
 */
public class AuthenticationException extends RestfulSecurityException {

    private static final long serialVersionUID = -6282965303632550788L;

    public AuthenticationException(String message) {
        super(message);
    }

}
