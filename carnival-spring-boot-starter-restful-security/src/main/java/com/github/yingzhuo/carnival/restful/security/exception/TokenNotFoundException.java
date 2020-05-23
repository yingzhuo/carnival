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
 * @author 应卓
 */
public class TokenNotFoundException extends AuthenticationException {

    public TokenNotFoundException() {
    }

    public TokenNotFoundException(HttpServletRequest request) {
        super(request);
    }

    public TokenNotFoundException(String message, HttpServletRequest request) {
        super(message, request);
    }

}
