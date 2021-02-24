/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.domain.proto.storage;

import com.github.yingzhuo.carnival.fastdfs.domain.proto.AbstractCommand;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.storage.internal.StorageDownloadRequest;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.storage.internal.StorageDownloadResponse;

/**
 * 文件下载命令
 *
 * @author tobato
 */
public class StorageDownloadCommand<T> extends AbstractCommand<T> {

    public StorageDownloadCommand(String groupName, String path, long fileOffset, long downloadBytes,
                                  DownloadCallback<T> callback) {
        this.request = new StorageDownloadRequest(groupName, path, fileOffset, downloadBytes);
        // 输出响应
        this.response = new StorageDownloadResponse<T>(callback);
    }

    public StorageDownloadCommand(String groupName, String path, DownloadCallback<T> callback) {
        this.request = new StorageDownloadRequest(groupName, path, 0, 0);
        // 输出响应
        this.response = new StorageDownloadResponse<T>(callback);
    }

}
