/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.domain.proto.mapper;

/**
 * 映射例外
 *
 * @author tobato
 */
public class FastDFSColumnMappingException extends RuntimeException {

    protected FastDFSColumnMappingException() {
    }

    protected FastDFSColumnMappingException(String message, Throwable cause, boolean enableSuppression,
                                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    protected FastDFSColumnMappingException(String message, Throwable cause) {
        super(message, cause);
    }

    protected FastDFSColumnMappingException(String message) {
        super(message);
    }

    protected FastDFSColumnMappingException(Throwable cause) {
        super(cause);
    }

}
