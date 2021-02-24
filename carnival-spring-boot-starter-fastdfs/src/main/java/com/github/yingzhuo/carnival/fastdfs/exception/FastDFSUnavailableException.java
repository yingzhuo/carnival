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
 * 非fastdfs本身的错误码抛出的异常，取服务端连接取不到时抛出的异常
 *
 * @author yuqihuang
 */
public class FastDFSUnavailableException extends FastDFSException {

    public FastDFSUnavailableException(String message) {
        super(message);
    }

    public FastDFSUnavailableException(String message, Throwable t) {
        super(message, t);
    }

}
