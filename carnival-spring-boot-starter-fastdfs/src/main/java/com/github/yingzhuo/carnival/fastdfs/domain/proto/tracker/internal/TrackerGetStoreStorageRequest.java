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
import com.github.yingzhuo.carnival.fastdfs.domain.proto.ProtoHead;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.Request;

/**
 * 获取存储节点请求
 *
 * @author tobato
 */
public class TrackerGetStoreStorageRequest extends Request {

    /**
     * 获取存储节点
     */
    public TrackerGetStoreStorageRequest() {
        this.head = new ProtoHead(CmdConstants.TRACKER_PROTO_CMD_SERVICE_QUERY_STORE_WITHOUT_GROUP_ONE);
    }

}
