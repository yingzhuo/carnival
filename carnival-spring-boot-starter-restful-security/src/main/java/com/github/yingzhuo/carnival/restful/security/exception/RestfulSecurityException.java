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

import com.github.yingzhuo.carnival.common.mvc.RequestInterruptedException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 应卓
 */
public class RestfulSecurityException extends RequestInterruptedException {

    public RestfulSecurityException(HttpServletRequest request) {
        this(null, request);
    }

    public RestfulSecurityException(String message, HttpServletRequest request) {
        super(message, request);
    }

}
