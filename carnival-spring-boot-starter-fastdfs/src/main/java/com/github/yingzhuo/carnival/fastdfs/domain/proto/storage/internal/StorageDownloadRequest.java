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

/**
 * 文件下载请求
 *
 * @author tobato
 */
public class StorageDownloadRequest extends Request {

    /**
     * 开始位置
     */
    @Column(index = 0)
    private long fileOffset;
    /**
     * 读取文件长度
     */
    @Column(index = 1)
    private long downloadBytes;
    /**
     * 组名
     */
    @Column(index = 2, max = OtherConstants.FDFS_GROUP_NAME_MAX_LEN)
    private String groupName;
    /**
     * 文件路径
     */
    @Column(index = 3, dynamicField = DynamicFieldType.ALL_REST_BYTE)
    private String path;

    public StorageDownloadRequest(String groupName, String path, long fileOffset, long downloadBytes) {
        super.head = new ProtoHead(CmdConstants.STORAGE_PROTO_CMD_DOWNLOAD_FILE);
        this.groupName = groupName;
        this.downloadBytes = downloadBytes;
        this.path = path;
        this.fileOffset = fileOffset;
    }

    public long getFileOffset() {
        return fileOffset;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getPath() {
        return path;
    }

    public long getDownloadBytes() {
        return downloadBytes;
    }
}
