/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.nonce.exception;

import com.github.yingzhuo.carnival.common.mvc.RequestInterruptedException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 应卓
 * @since 1.6.29
 */
public class NonceTokenException extends RequestInterruptedException {

    public NonceTokenException(String message, HttpServletRequest request) {
        super(message, request);
    }

}
