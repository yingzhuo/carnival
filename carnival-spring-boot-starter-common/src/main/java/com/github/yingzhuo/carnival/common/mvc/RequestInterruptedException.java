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
import java.io.Serializable;

/**
 * @author 应卓
 * @since 1.6.7
 */
public class RequestInterruptedException extends RuntimeException implements Serializable {

    private RequestMethod requestMethod;
    private String path;

    private RequestInterruptedException(String message, RequestMethod requestMethod, String path) {
        super(message);
        this.requestMethod = requestMethod;
        this.path = path;
    }

    public RequestInterruptedException(String message, HttpServletRequest request) {
        this(message,
                request != null ? RequestMethod.valueOf(request.getMethod().toUpperCase()) : null,
                request != null ? request.getRequestURI() : null);
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public String getPath() {
        return path;
    }

}
