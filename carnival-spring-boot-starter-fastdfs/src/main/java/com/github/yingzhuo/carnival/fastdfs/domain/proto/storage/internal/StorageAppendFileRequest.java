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
 * 文件上传命令
 *
 * @author tobato
 */
public class StorageAppendFileRequest extends Request {

    /**
     * 文件路径长度
     */
    @Column(index = 0)
    private long pathSize;

    /**
     * 发送文件长度
     */
    @Column(index = 1)
    private long fileSize;

    /**
     * 文件路径
     */
    @Column(index = 2, dynamicField = DynamicFieldType.ALL_REST_BYTE)
    private String path;

    public StorageAppendFileRequest(InputStream inputStream, long fileSize, String path) {
        super.head = new ProtoHead(CmdConstants.STORAGE_PROTO_CMD_APPEND_FILE);
        this.inputFile = inputStream;
        this.fileSize = fileSize;
        this.path = path;
    }

    /**
     * 打包参数
     */
    @Override
    public byte[] encodeParam(Charset charset) {
        // 运行时参数再此计算值
        this.pathSize = path.getBytes(charset).length;
        return super.encodeParam(charset);
    }

    public long getPathSize() {
        return pathSize;
    }

    public void setPathSize(long pathSize) {
        this.pathSize = pathSize;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

}
