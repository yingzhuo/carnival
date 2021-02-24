/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.domain.proto.storage.internal;

import com.github.yingzhuo.carnival.fastdfs.domain.proto.CmdConstants;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.ProtoHead;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.Request;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.mapper.Column;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.mapper.DynamicFieldType;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * 文件修改请求
 *
 * @author tobato
 */
public class StorageModifyRequest extends Request {

    /**
     * 文件路径长度
     */
    @Column(index = 0)
    private long pathSize;

    /**
     * 开始位置
     */
    @Column(index = 1)
    private long fileOffset;

    /**
     * 发送文件长度
     */
    @Column(index = 2)
    private long fileSize;

    /**
     * 文件路径
     */
    @Column(index = 3, dynamicField = DynamicFieldType.ALL_REST_BYTE)
    private String path;

    public StorageModifyRequest(InputStream inputStream, long fileSize, String path, long fileOffset) {
        super.head = new ProtoHead(CmdConstants.STORAGE_PROTO_CMD_MODIFY_FILE);
        this.inputFile = inputStream;
        this.fileSize = fileSize;
        this.path = path;
        this.fileOffset = fileOffset;
    }

    @Override
    public byte[] encodeParam(Charset charset) {
        // 运行时参数在此计算值
        this.pathSize = path.getBytes(charset).length;
        return super.encodeParam(charset);
    }

    public long getPathSize() {
        return pathSize;
    }

    public long getFileOffset() {
        return fileOffset;
    }

    public String getPath() {
        return path;
    }

    @Override
    public long getFileSize() {
        return fileSize;
    }

}
