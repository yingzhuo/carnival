/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.domain.proto.tracker.internal;

import com.github.yingzhuo.carnival.fastdfs.domain.proto.CmdConstants;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.OtherConstants;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.ProtoHead;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.Request;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.mapper.Column;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.mapper.DynamicFieldType;

/**
 * 列出存储状态
 *
 * @author tobato
 */
public class TrackerListStorageRequest extends Request {

    /**
     * 组名
     */
    @Column(index = 0, max = OtherConstants.FDFS_GROUP_NAME_MAX_LEN)
    private String groupName;

    /**
     * 存储服务器ip地址
     */
    @Column(index = 1, max = OtherConstants.FDFS_IP_ADDR_SIZE - 1, dynamicField = DynamicFieldType.NULLABLE)
    private String storageIpAddr;

    public TrackerListStorageRequest() {
        head = new ProtoHead(CmdConstants.TRACKER_PROTO_CMD_SERVER_LIST_STORAGE);
    }

    /**
     * 列举存储服务器状态
     */
    public TrackerListStorageRequest(String groupName, String storageIpAddr) {
        this.groupName = groupName;
        this.storageIpAddr = storageIpAddr;
    }

    /**
     * 列举组当中存储节点状态
     */
    public TrackerListStorageRequest(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getStorageIpAddr() {
        return storageIpAddr;
    }

}
