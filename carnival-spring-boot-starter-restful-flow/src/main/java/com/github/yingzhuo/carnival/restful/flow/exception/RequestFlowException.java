/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.flow.exception;

import com.github.yingzhuo.carnival.common.mvc.AbstractRequestInterruptedException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 应卓
 * @since 1.3.6
 */
public class RequestFlowException extends AbstractRequestInterruptedException {

    public RequestFlowException(HttpServletRequest request) {
        this(null, request);
    }

    public RequestFlowException(String message, HttpServletRequest request) {
        super(message, request);
    }

}
