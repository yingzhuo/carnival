/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.mvc;

import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 应卓
 * @since 1.6.8
 */
public abstract class RequestInterruptedException extends RuntimeException {

    private final RequestMethod method;
    private final String path;

    public RequestInterruptedException(String message, HttpServletRequest request) {
        super(message);
        this.method = request != null ? RequestMethod.valueOf(request.getMethod().toUpperCase()) : null;
        this.path = request != null ? request.getRequestURI() : null;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

}
