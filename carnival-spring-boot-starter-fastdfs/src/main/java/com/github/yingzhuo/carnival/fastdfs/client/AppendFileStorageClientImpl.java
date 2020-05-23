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

import com.github.yingzhuo.carnival.fastdfs.domain.conn.StorageConnectionManager;
import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.StorageNode;
import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.StorageNodeInfo;
import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.StorePath;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.storage.StorageAppendFileCommand;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.storage.StorageModifyCommand;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.storage.StorageTruncateCommand;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.storage.StorageUploadFileCommand;

import java.io.InputStream;

/**
 * 存储服务客户端接口实现
 *
 * @author tobato
 */
public class AppendFileStorageClientImpl extends GenerateStorageClientImpl implements AppendFileStorageClient {

    public AppendFileStorageClientImpl(TrackerClient trackerClient, StorageConnectionManager manager) {
        super(trackerClient, manager);
    }

    @Override
    public StorePath uploadAppenderFile(String groupName, InputStream inputStream, long fileSize, String fileExtName) {
        StorageNode client = trackerClient.getStoreStorage(groupName);
        StorageUploadFileCommand command = new StorageUploadFileCommand(client.getStoreIndex(), inputStream,
                fileExtName, fileSize, true);
        return connectionManager.executeCommand(client.getInetSocketAddress(), command);
    }

    @Override
    public void appendFile(String groupName, String path, InputStream inputStream, long fileSize) {
        StorageNodeInfo client = trackerClient.getUpdateStorage(groupName, path);
        StorageAppendFileCommand command = new StorageAppendFileCommand(inputStream, fileSize, path);
        connectionManager.executeCommand(client.getInetSocketAddress(), command);
    }

    @Override
    public void modifyFile(String groupName, String path, InputStream inputStream, long fileSize, long fileOffset) {
        StorageNodeInfo client = trackerClient.getUpdateStorage(groupName, path);
        StorageModifyCommand command = new StorageModifyCommand(path, inputStream, fileSize, fileOffset);
        connectionManager.executeCommand(client.getInetSocketAddress(), command);
    }

    @Override
    public void truncateFile(String groupName, String path, long truncatedFileSize) {
        StorageNodeInfo client = trackerClient.getUpdateStorage(groupName, path);
        StorageTruncateCommand command = new StorageTruncateCommand(path, truncatedFileSize);
        connectionManager.executeCommand(client.getInetSocketAddress(), command);
    }

    @Override
    public void truncateFile(String groupName, String path) {
        long truncatedFileSize = 0;
        truncateFile(groupName, path, truncatedFileSize);
    }

}
