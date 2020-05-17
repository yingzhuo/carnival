/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.norepeated.exception;

import com.github.yingzhuo.carnival.common.mvc.RequestInterruptedException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 应卓
 * @since 1.6.7
 */
public class RepeatedRequestException extends RequestInterruptedException {

    public RepeatedRequestException(HttpServletRequest request) {
        this(null, request);
    }

    public RepeatedRequestException(String message, HttpServletRequest request) {
        super(message, request);
    }

}
