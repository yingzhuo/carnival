/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.constant;

/**
 * @author 应卓
 * @since 1.6.13
 */
public class NoSuchConstantException extends RuntimeException {

    public NoSuchConstantException() {
    }

    public NoSuchConstantException(String message) {
        super(message);
    }

}
