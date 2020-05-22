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
 * 不支持的图片格式
 *
 * @author tobato
 */
public class FastDFSUnsupportedImageFormatException extends FastDFSException {

    public FastDFSUnsupportedImageFormatException() {
        this(null);
    }

    public FastDFSUnsupportedImageFormatException(String message) {
        super(message);
    }

}
