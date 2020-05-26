/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.domain.proto;

import com.github.yingzhuo.carnival.fastdfs.domain.proto.mapper.ObjectMetaData;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.mapper.ParamMapperUtils;

import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.Charset;

/**
 * 交易请求
 *
 * @author tobato
 */
public abstract class Request implements Serializable {

    protected ProtoHead head;

    protected InputStream inputFile;

    /**
     * 获取报文头(包内可见)
     */
    ProtoHead getHead() {
        return head;
    }

    public byte[] getHeadByte(Charset charset) {
        // 设置报文长度
        head.setContentLength(getBodyLength(charset));
        // 返回报文byte
        return head.toBytes();
    }

    public byte[] encodeParam(Charset charset) {
        return ParamMapperUtils.toByte(this, charset);
    }

    protected long getBodyLength(Charset charset) {
        ObjectMetaData objectMetaData = ParamMapperUtils.getObjectMap(this.getClass());
        return objectMetaData.getFieldsSendTotalByteSize(this, charset) + getFileSize();
    }

    public InputStream getInputFile() {
        return inputFile;
    }

    public long getFileSize() {
        return 0;
    }

}
