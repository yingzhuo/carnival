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
import com.github.yingzhuo.carnival.fastdfs.domain.proto.OtherConstants;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.ProtoHead;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.Request;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.mapper.Column;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.mapper.DynamicFieldType;

import java.io.InputStream;

/**
 * 从文件上传命令
 *
 * @author tobato
 */
public class StorageUploadSlaveFileRequest extends Request {

    /**
     * 名称前缀
     */
    @Column(index = 2, max = OtherConstants.FDFS_FILE_PREFIX_MAX_LEN)
    private final String prefixName;

    /**
     * 主文件名
     */
    @Column(index = 4, dynamicField = DynamicFieldType.ALL_REST_BYTE)
    private final String masterFilename;

    /**
     * 主文件名长度
     */
    @Column(index = 0)
    private long masterFileNameSize;

    /**
     * 发送文件长度
     */
    @Column(index = 1)
    private long fileSize;

    /**
     * 文件扩展名
     */
    @Column(index = 3, max = OtherConstants.FDFS_FILE_EXT_NAME_MAX_LEN)
    private String fileExtName;

    public StorageUploadSlaveFileRequest(InputStream inputStream, long fileSize, String masterFilename,
                                         String prefixName, String fileExtName) {

        super.head = new ProtoHead(CmdConstants.STORAGE_PROTO_CMD_UPLOAD_SLAVE_FILE);
        this.inputFile = inputStream;
        this.fileSize = fileSize;
        this.masterFileNameSize = masterFilename.length();
        this.masterFilename = masterFilename;
        this.fileExtName = fileExtName;
        this.prefixName = prefixName;
    }

    public long getMasterFileNameSize() {
        return masterFileNameSize;
    }

    public void setMasterFileNameSize(long masterFileNameSize) {
        this.masterFileNameSize = masterFileNameSize;
    }

    public String getPrefixName() {
        return prefixName;
    }

    public String getMasterFilename() {
        return masterFilename;
    }

    public String getFileExtName() {
        return fileExtName;
    }

    public void setFileExtName(String fileExtName) {
        this.fileExtName = fileExtName;
    }

    @Override
    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

}
