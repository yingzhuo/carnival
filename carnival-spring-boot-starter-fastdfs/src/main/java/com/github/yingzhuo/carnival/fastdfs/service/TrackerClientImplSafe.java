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

import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.GroupState;
import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.StorageNode;
import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.StorageNodeInfo;
import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.StorageState;

import java.util.List;

public class TrackerClientImplSafe implements TrackerClient {

    private final TrackerClient delegate;

    public TrackerClientImplSafe(TrackerClient delegate) {
        this.delegate = delegate;
    }

    @Override
    public StorageNode getStoreStorage() {
        return delegate.getStoreStorage();
    }

    @Override
    public StorageNode getStoreStorage(String groupName) {
        return delegate.getStoreStorage(groupName);
    }

    @Override
    public StorageNodeInfo getFetchStorage(String groupName, String filename) {
        return delegate.getFetchStorage(groupName, filename);
    }

    @Override
    public StorageNodeInfo getUpdateStorage(String groupName, String filename) {
        return delegate.getUpdateStorage(groupName, filename);
    }

    @Override
    public List<GroupState> listGroups() {
        return delegate.listGroups();
    }

    @Override
    public List<StorageState> listStorage(String groupName) {
        return delegate.listStorage(groupName);
    }

    @Override
    public List<StorageState> listStorage(String groupName, String storageIpAddr) {
        return delegate.listStorage(groupName, storageIpAddr);
    }

    @Override
    public void deleteStorage(String groupName, String storageIpAddr) {
        throw new UnsupportedOperationException();
    }

}
