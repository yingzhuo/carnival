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

import javax.servlet.http.HttpServletRequest;

/**
 * @author 应卓
 * @since 1.6.7
 */
public class NoTokenFoundException extends RepeatedRequestException {

    public NoTokenFoundException(HttpServletRequest request) {
        super(request);
    }

    public NoTokenFoundException(String message, HttpServletRequest request) {
        super(message, request);
    }

}
