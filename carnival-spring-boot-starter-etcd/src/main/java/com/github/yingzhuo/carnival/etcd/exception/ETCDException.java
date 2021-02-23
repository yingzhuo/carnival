/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.etcd.exception;

/**
 * @author 应卓
 * @since 1.6.11
 */
public class ETCDException extends RuntimeException {

    public ETCDException() {
    }

    public ETCDException(Throwable cause) {
        super(cause);
    }

}
