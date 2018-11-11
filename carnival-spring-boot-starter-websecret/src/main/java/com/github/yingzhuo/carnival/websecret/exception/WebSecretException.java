/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.websecret.exception;

/**
 * @author 应卓
 */
public class WebSecretException extends RuntimeException {

    private static final long serialVersionUID = -6287995977875590347L;

    public WebSecretException() {
        super();
    }

    public WebSecretException(String message) {
        super(message);
    }

}
