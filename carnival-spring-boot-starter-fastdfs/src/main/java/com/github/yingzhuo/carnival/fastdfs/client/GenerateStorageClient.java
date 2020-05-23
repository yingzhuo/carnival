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

import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.FileInfo;
import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.MetaData;
import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.StorePath;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.storage.DownloadCallback;

import java.io.InputStream;
import java.util.Set;

/**
 * 基本文件存储客户端操作
 *
 * @author tobato
 */
public interface GenerateStorageClient {

    /*
     * 上传文件(文件不可修改)
     */
    public StorePath uploadFile(String groupName, InputStream inputStream, long fileSize, String fileExtName);

    /*
     * 上传从文件
     */
    public StorePath uploadSlaveFile(String groupName, String masterFilename, InputStream inputStream, long fileSize,
                                     String prefixName, String fileExtName);

    /*
     * 获取文件元信息
     */
    public Set<MetaData> getMetadata(String groupName, String path);

    /*
     * 修改文件元信息（覆盖）
     */
    public void overwriteMetadata(String groupName, String path, Set<MetaData> metaDataSet);

    /*
     * 修改文件元信息（合并）
     */
    public void mergeMetadata(String groupName, String path, Set<MetaData> metaDataSet);

    /*
     * 查看文件的信息
     */
    public FileInfo queryFileInfo(String groupName, String path);

    /*
     * 删除文件
     */
    public void deleteFile(String groupName, String path);

    /*
     * 下载整个文件
     */
    public <T> T downloadFile(String groupName, String path, DownloadCallback<T> callback);

    /*
     * 下载文件片段
     */
    public <T> T downloadFile(String groupName, String path, long fileOffset, long fileSize, DownloadCallback<T> callback);

}
