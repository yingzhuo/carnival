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
import java.nio.charset.Charset;

/**
 * Fdfs交易请求基类
 *
 * @author tobato
 */
public abstract class Request {

    /**
     * 报文头
     */
    protected ProtoHead head;
    /**
     * 发送文件
     */
    protected InputStream inputFile;

    /**
     * 获取报文头(包内可见)
     */
    ProtoHead getHead() {
        return head;
    }

    /**
     * 获取报文头
     */
    public byte[] getHeadByte(Charset charset) {
        // 设置报文长度
        head.setContentLength(getBodyLength(charset));
        // 返回报文byte
        return head.toBytes();
    }

    /**
     * 打包参数
     */
    public byte[] encodeParam(Charset charset) {
        return ParamMapperUtils.toByte(this, charset);
    }

    /**
     * 获取参数域长度
     */
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
