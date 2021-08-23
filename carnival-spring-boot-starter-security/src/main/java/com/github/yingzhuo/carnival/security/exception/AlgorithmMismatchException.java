/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.exception;

import org.springframework.security.authentication.BadCredentialsException;

/**
 * @author 应卓
 * @since 1.10.2
 */
public class AlgorithmMismatchException extends BadCredentialsException {

    public AlgorithmMismatchException(String msg) {
        super(msg);
    }

    public AlgorithmMismatchException(String msg, Throwable t) {
        super(msg, t);
    }

}