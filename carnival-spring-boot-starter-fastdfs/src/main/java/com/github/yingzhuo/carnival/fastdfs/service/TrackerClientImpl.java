/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.service;

import com.github.yingzhuo.carnival.fastdfs.domain.conn.TrackerConnectionManager;
import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.GroupState;
import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.StorageNode;
import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.StorageNodeInfo;
import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.StorageState;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.tracker.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 目录服务客户端默认实现
 *
 * @author tobato
 */
public class TrackerClientImpl implements TrackerClient {

    private final TrackerConnectionManager manager;

    public TrackerClientImpl(TrackerConnectionManager manager) {
        this.manager = manager;
    }

    /**
     * 获取存储节点
     */
    @Override
    public StorageNode getStoreStorage() {
        TrackerGetStoreStorageCommand command = new TrackerGetStoreStorageCommand();
        return manager.executeTrackerCommand(command);
    }

    /**
     * 按组获取存储节点
     */
    @Override
    public StorageNode getStoreStorage(String groupName) {
        TrackerGetStoreStorageCommand command;
        if (StringUtils.isBlank(groupName)) {
            command = new TrackerGetStoreStorageCommand();
        } else {
            command = new TrackerGetStoreStorageCommand(groupName);
        }

        return manager.executeTrackerCommand(command);
    }

    /**
     * 获取源服务器
     */
    @Override
    public StorageNodeInfo getFetchStorage(String groupName, String filename) {
        TrackerGetFetchStorageCommand command = new TrackerGetFetchStorageCommand(groupName, filename, false);
        return manager.executeTrackerCommand(command);
    }

    /**
     * 获取更新服务器
     */
    @Override
    public StorageNodeInfo getUpdateStorage(String groupName, String filename) {
        TrackerGetFetchStorageCommand command = new TrackerGetFetchStorageCommand(groupName, filename, true);
        return manager.executeTrackerCommand(command);
    }

    /**
     * 列出组
     */
    @Override
    public List<GroupState> listGroups() {
        TrackerListGroupsCommand command = new TrackerListGroupsCommand();
        return manager.executeTrackerCommand(command);
    }

    /**
     * 按组列出存储状态
     */
    @Override
    public List<StorageState> listStorage(String groupName) {
        TrackerListStorageCommand command = new TrackerListStorageCommand(groupName);
        return manager.executeTrackerCommand(command);
    }

    /**
     * 按ip列出存储状态
     */
    @Override
    public List<StorageState> listStorage(String groupName, String storageIpAddr) {
        TrackerListStorageCommand command = new TrackerListStorageCommand(groupName, storageIpAddr);
        return manager.executeTrackerCommand(command);
    }

    /**
     * 删除存储节点
     */
    @Override
    public void deleteStorage(String groupName, String storageIpAddr) {
        TrackerDeleteStorageCommand command = new TrackerDeleteStorageCommand(groupName, storageIpAddr);
        manager.executeTrackerCommand(command);
    }

}
