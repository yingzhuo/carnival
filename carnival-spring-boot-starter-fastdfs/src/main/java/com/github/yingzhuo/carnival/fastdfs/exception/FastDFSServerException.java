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

import com.github.yingzhuo.carnival.fastdfs.domain.proto.ErrorCodeConstants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * fastdfs服务端返回的错误码构成的异常
 *
 * @author yuqih
 * @author tobato
 */
public class FastDFSServerException extends FastDFSException {

    /**
     * 错误对照表
     */
    private static final Map<Integer, String> CODE_MESSAGE_MAPPING;

    static {
        Map<Integer, String> mapping = new HashMap<>();
        mapping.put((int) ErrorCodeConstants.ERR_NO_ENOENT, "找不到节点或文件");
        mapping.put((int) ErrorCodeConstants.ERR_NO_EIO, "服务端发生io异常");
        mapping.put((int) ErrorCodeConstants.ERR_NO_EINVAL, "无效的参数");
        mapping.put((int) ErrorCodeConstants.ERR_NO_EBUSY, "服务端忙");
        mapping.put((int) ErrorCodeConstants.ERR_NO_ENOSPC, "没有足够的存储空间");
        mapping.put((int) ErrorCodeConstants.ERR_NO_CONNREFUSED, "服务端拒绝连接");
        mapping.put((int) ErrorCodeConstants.ERR_NO_EALREADY, "文件已经存在？");
        CODE_MESSAGE_MAPPING = Collections.unmodifiableMap(mapping);
    }

    private int errorCode;

    private FastDFSServerException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public static FastDFSServerException byCode(int errorCode) {
        String message = CODE_MESSAGE_MAPPING.get(errorCode);
        if (message == null) {
            message = "未知";
        }
        message = "错误码：" + errorCode + "，错误信息：" + message;

        return new FastDFSServerException(errorCode, message);
    }

    /**
     * @return the errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }

}
