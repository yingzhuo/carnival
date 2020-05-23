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

/**
 * 移除存储服务器
 *
 * @author tobato
 */
public class TrackerDeleteStorageRequest extends Request {

    /**
     * 组名
     */
    @Column(index = 0, max = OtherConstants.FDFS_GROUP_NAME_MAX_LEN)
    private String groupName;

    /**
     * 存储ip
     */
    @Column(index = 1, max = OtherConstants.FDFS_IP_ADDR_SIZE - 1)
    private String storageIpAddr;

    /**
     * 获取文件源服务器
     */
    public TrackerDeleteStorageRequest(String groupName, String storageIpAddr) {
        super.head = new ProtoHead(CmdConstants.TRACKER_PROTO_CMD_SERVER_DELETE_STORAGE);
        this.groupName = groupName;
        this.storageIpAddr = storageIpAddr;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getStorageIpAddr() {
        return storageIpAddr;
    }

}
