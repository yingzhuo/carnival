/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.jwt.exception;

/**
 * 加密算法不匹配
 *
 * @author 应卓
 * @see com.auth0.jwt.exceptions.AlgorithmMismatchException
 */
public class AlgorithmMismatchException extends InvalidTokenException {

    private static final long serialVersionUID = -7975260628952282628L;

    public AlgorithmMismatchException() {
        super();
    }

    public AlgorithmMismatchException(String message) {
        super(message);
    }

    public AlgorithmMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlgorithmMismatchException(Throwable cause) {
        super(cause);
    }

}
