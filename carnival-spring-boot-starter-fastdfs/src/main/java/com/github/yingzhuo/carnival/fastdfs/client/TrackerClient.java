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
 * 目录服务(Tracker)客户端接口
 *
 * @author tobato
 * @since 1.6.10
 */
public interface TrackerClient {

    /**
     * 获取存储节点
     */
    public StorageNode getStoreStorage();

    /**
     * 按组获取存储节点
     */
    public StorageNode getStoreStorage(String groupName);

    /**
     * 获取读取存储节点
     */
    public StorageNodeInfo getFetchStorage(String groupName, String filename);

    /**
     * 获取更新节点
     */
    public StorageNodeInfo getUpdateStorage(String groupName, String filename);

    /**
     * 获取组状态
     */
    public List<GroupState> listGroups();

    /**
     * 按组名获取存储节点状态
     */
    public List<StorageState> listStorage(String groupName);

    /**
     * 获取存储状态
     */
    public List<StorageState> listStorage(String groupName, String storageIpAddr);

    /**
     * 删除存储节点
     */
    public void deleteStorage(String groupName, String storageIpAddr);

}
