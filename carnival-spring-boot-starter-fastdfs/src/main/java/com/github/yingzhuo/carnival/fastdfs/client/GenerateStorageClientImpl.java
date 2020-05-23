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
import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.*;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.storage.*;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.storage.enums.StorageMetadataSetType;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Set;

/**
 * 基本存储客户端操作实现
 *
 * @author tobato
 * @since 1.6.10
 */
@Slf4j
public class GenerateStorageClientImpl implements GenerateStorageClient {

    protected final TrackerClient trackerClient;
    protected final StorageConnectionManager connectionManager;

    public GenerateStorageClientImpl(TrackerClient trackerClient, StorageConnectionManager manager) {
        this.trackerClient = trackerClient;
        this.connectionManager = manager;
    }

    @Override
    public StorePath uploadFile(String groupName, InputStream inputStream, long fileSize, String fileExtName) {
        StorageNode client = trackerClient.getStoreStorage(groupName);
        StorageUploadFileCommand command = new StorageUploadFileCommand(client.getStoreIndex(), inputStream,
                fileExtName, fileSize, false);
        return connectionManager.executeCommand(client.getInetSocketAddress(), command);
    }

    @Override
    public StorePath uploadSlaveFile(String groupName, String masterFilename, InputStream inputStream, long fileSize,
                                     String prefixName, String fileExtName) {
        StorageNodeInfo client = trackerClient.getUpdateStorage(groupName, masterFilename);
        StorageUploadSlaveFileCommand command = new StorageUploadSlaveFileCommand(inputStream, fileSize, masterFilename,
                prefixName, fileExtName);
        return connectionManager.executeCommand(client.getInetSocketAddress(), command);
    }

    @Override
    public Set<MetaData> getMetadata(String groupName, String path) {
        StorageNodeInfo client = trackerClient.getFetchStorage(groupName, path);
        StorageGetMetadataCommand command = new StorageGetMetadataCommand(groupName, path);
        return connectionManager.executeCommand(client.getInetSocketAddress(), command);
    }

    @Override
    public void overwriteMetadata(String groupName, String path, Set<MetaData> metaDataSet) {
        StorageNodeInfo client = trackerClient.getUpdateStorage(groupName, path);
        StorageSetMetadataCommand command = new StorageSetMetadataCommand(groupName, path, metaDataSet,
                StorageMetadataSetType.STORAGE_SET_METADATA_FLAG_OVERWRITE);
        connectionManager.executeCommand(client.getInetSocketAddress(), command);
    }

    @Override
    public void mergeMetadata(String groupName, String path, Set<MetaData> metaDataSet) {
        StorageNodeInfo client = trackerClient.getUpdateStorage(groupName, path);
        StorageSetMetadataCommand command = new StorageSetMetadataCommand(groupName, path, metaDataSet,
                StorageMetadataSetType.STORAGE_SET_METADATA_FLAG_MERGE);
        connectionManager.executeCommand(client.getInetSocketAddress(), command);
    }

    @Override
    public FileInfo queryFileInfo(String groupName, String path) {
        StorageNodeInfo client = trackerClient.getFetchStorage(groupName, path);
        StorageQueryFileInfoCommand command = new StorageQueryFileInfoCommand(groupName, path);
        return connectionManager.executeCommand(client.getInetSocketAddress(), command);
    }

    @Override
    public void deleteFile(String groupName, String path) {
        StorageNodeInfo client = trackerClient.getUpdateStorage(groupName, path);
        StorageDeleteFileCommand command = new StorageDeleteFileCommand(groupName, path);
        connectionManager.executeCommand(client.getInetSocketAddress(), command);
    }

    @Override
    public <T> T downloadFile(String groupName, String path, DownloadCallback<T> callback) {
        long fileOffset = 0;
        long fileSize = 0;
        return downloadFile(groupName, path, fileOffset, fileSize, callback);
    }

    @Override
    public <T> T downloadFile(String groupName, String path, long fileOffset, long fileSize,
                              DownloadCallback<T> callback) {
        StorageNodeInfo client = trackerClient.getFetchStorage(groupName, path);
        StorageDownloadCommand<T> command = new StorageDownloadCommand<T>(groupName, path, fileOffset, fileSize, callback);
        return connectionManager.executeCommand(client.getInetSocketAddress(), command);
    }

}
