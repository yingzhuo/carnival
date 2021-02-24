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
 * 上传图片例外
 *
 * @author tobato
 */
public class FastDFSUploadImageException extends FastDFSException {

    protected FastDFSUploadImageException(String message) {
        super(message);
    }

    public FastDFSUploadImageException(String message, Throwable cause) {
        super(message, cause);
    }

}
