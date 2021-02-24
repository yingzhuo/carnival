/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.exception;

/**
 * 封装fastdfs的异常，使用运行时异常
 *
 * @author yuqih
 * @author tobato
 */
public abstract class FastDFSException extends RuntimeException {

    protected FastDFSException(String message) {
        super(message);
    }

    protected FastDFSException(String message, Throwable cause) {
        super(message, cause);
    }

}
