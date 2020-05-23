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
 * 按分组获取存储节点
 *
 * @author tobato
 */
public class TrackerGetStoreStorageWithGroupRequest extends Request {

    /**
     * 分组定义
     */
    @Column(index = 0, max = OtherConstants.FDFS_GROUP_NAME_MAX_LEN)
    private final String groupName;

    /**
     * 获取存储节点
     */
    public TrackerGetStoreStorageWithGroupRequest(String groupName) {
        super.head = new ProtoHead(CmdConstants.TRACKER_PROTO_CMD_SERVICE_QUERY_STORE_WITH_GROUP_ONE);
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

}
