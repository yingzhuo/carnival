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

import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.MetaData;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.CmdConstants;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.OtherConstants;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.ProtoHead;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.Request;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.mapper.Column;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.mapper.DynamicFieldType;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.mapper.MetadataMapper;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.storage.enums.StorageMetadataSetType;

import java.nio.charset.Charset;
import java.util.Set;

/**
 * 设置文件标签
 *
 * @author tobato
 */
public class StorageSetMetadataRequest extends Request {

    /**
     * 文件名byte长度
     */
    @Column(index = 0)
    private int fileNameByteLength;

    /**
     * 元数据byte长度
     */
    @Column(index = 1)
    private int metaDataByteLength;

    /**
     * 操作标记（重写/覆盖）
     */
    @Column(index = 2)
    private byte opFlag;

    /**
     * 组名
     */
    @Column(index = 3, max = OtherConstants.FDFS_GROUP_NAME_MAX_LEN)
    private String groupName;

    /**
     * 文件路径
     */
    @Column(index = 4, dynamicField = DynamicFieldType.ALL_REST_BYTE)
    private String path;

    /**
     * 元数据
     */
    @Column(index = 5, dynamicField = DynamicFieldType.METADATA)
    private Set<MetaData> metaDataSet;

    /**
     * 设置文件元数据
     */
    public StorageSetMetadataRequest(String groupName, String path, Set<MetaData> metaDataSet,
                                     StorageMetadataSetType type) {
        super.head = new ProtoHead(CmdConstants.STORAGE_PROTO_CMD_SET_METADATA);
        this.groupName = groupName;
        this.path = path;
        this.metaDataSet = metaDataSet;
        this.opFlag = type.getType();
    }

    /**
     * 打包参数
     */
    @Override
    public byte[] encodeParam(Charset charset) {
        // 运行时参数在此计算值
        this.fileNameByteLength = path.getBytes(charset).length;
        this.metaDataByteLength = getMetaDataSetByteSize(charset);
        return super.encodeParam(charset);
    }

    /**
     * 获取metaDataSet长度
     */
    private int getMetaDataSetByteSize(Charset charset) {
        return MetadataMapper.toByte(metaDataSet, charset).length;
    }

    public String getGroupName() {
        return groupName;
    }

    public Set<MetaData> getMetaDataSet() {
        return metaDataSet;
    }

    public byte getOpFlag() {
        return opFlag;
    }

    public String getPath() {
        return path;
    }

    public int getFileNameByteLength() {
        return fileNameByteLength;
    }

    public int getMetaDataByteLength() {
        return metaDataByteLength;
    }
}
