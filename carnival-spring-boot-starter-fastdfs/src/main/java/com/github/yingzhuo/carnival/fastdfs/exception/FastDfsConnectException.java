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
 * 非fastdfs本身的错误码抛出的异常，socket连不上时抛出的异常
 *
 * @author yuqihuang
 * @author tobato
 */
public class FastDfsConnectException extends FastDFSUnavailableException {

    public FastDfsConnectException(String message, Throwable t) {
        super(message, t);
    }

}
