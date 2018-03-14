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
 * 授权异常
 *
 * @author 应卓
 * @see com.github.yingzhuo.carnival.restful.security.impl.CheckUtils
 * @see RestfulSecurityException
 */
public class AuthorizationException extends RestfulSecurityException {

    private static final long serialVersionUID = -137142203553204461L;

    public AuthorizationException() {
        this(null);
    }

    public AuthorizationException(String message) {
        super(message);
    }

}
