/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.domain.proto.tracker;

import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.StorageState;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.AbstractCommand;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.tracker.internal.TrackerListStorageRequest;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.tracker.internal.TrackerListStorageResponse;

import java.util.List;

/**
 * 列出组命令
 *
 * @author tobato
 */
public class TrackerListStorageCommand extends AbstractCommand<List<StorageState>> {

    public TrackerListStorageCommand(String groupName, String storageIpAddr) {
        super.request = new TrackerListStorageRequest(groupName, storageIpAddr);
        super.response = new TrackerListStorageResponse();
    }

    public TrackerListStorageCommand(String groupName) {
        super.request = new TrackerListStorageRequest(groupName);
        super.response = new TrackerListStorageResponse();
    }

}
