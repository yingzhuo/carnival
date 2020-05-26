/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.client;

import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.GroupState;
import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.StorageNode;
import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.StorageNodeInfo;
import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.StorageState;

import java.util.List;

/**
 * 目录服务客户端接口
 *
 * @author tobato
 * @since 1.6.10
 */
public interface TrackerClient {

    public StorageNode getStoreStorage();

    public StorageNode getStoreStorage(String groupName);

    public StorageNodeInfo getFetchStorage(String groupName, String filename);

    public StorageNodeInfo getUpdateStorage(String groupName, String filename);

    public List<GroupState> listGroups();

    public List<StorageState> listStorage(String groupName);

    public List<StorageState> listStorage(String groupName, String storageIpAddr);

    public void deleteStorage(String groupName, String storageIpAddr);

}
