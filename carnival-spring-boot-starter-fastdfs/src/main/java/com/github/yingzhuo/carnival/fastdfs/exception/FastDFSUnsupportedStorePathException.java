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
 * 从Url解析StorePath文件路径对象错误
 *
 * @author wuyf
 */
public class FastDFSUnsupportedStorePathException extends FastDFSException {

    public FastDFSUnsupportedStorePathException() {
        this(null);
    }

    public FastDFSUnsupportedStorePathException(String message) {
        super(message);
    }

}
