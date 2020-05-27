/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs;

import com.github.yingzhuo.carnival.fastdfs.client.FastFileStorageClient;
import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.MetaData;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.storage.BytesDownloadCallback;
import com.github.yingzhuo.carnival.fastdfs.properties.WebProperties;
import com.github.yingzhuo.carnival.spring.SpringUtils;
import lombok.val;
import lombok.var;

import java.io.InputStream;
import java.util.Collections;
import java.util.Set;

/**
 * @author 应卓
 * @since 1.6.10
 */
public final class FastDFS {

    private FastDFS() {
    }

    public static String upload(InputStream in, long fileSize, String fileExtName) {
        return upload(in, fileSize, fileExtName, Collections.emptySet());
    }

    public static String upload(InputStream in, long fileSize, String fileExtName, Set<MetaData> metaDataSet) {

        var prefix = SpringUtils.getBean(WebProperties.class).getUrl();
        if (prefix == null) {
            prefix = "";
        }

        val cli = SpringUtils.getBean(FastFileStorageClient.class);
        val data = cli.uploadFile(in, fileSize, fileExtName, metaDataSet);
        return prefix + data.getFullPath();
    }

    public static void delete(String filePath) {

        var prefix = SpringUtils.getBean(WebProperties.class).getUrl();
        if (prefix == null) {
            prefix = "";
        }

        if (filePath.startsWith(prefix)) {
            filePath = filePath.substring(prefix.length());
        }

        val cli = SpringUtils.getBean(FastFileStorageClient.class);
        cli.deleteFile(filePath);
    }

    public static byte[] download(String filePath) {
        var prefix = SpringUtils.getBean(WebProperties.class).getUrl();
        if (prefix == null) {
            prefix = "";
        }

        if (filePath.startsWith(prefix)) {
            filePath = filePath.substring(prefix.length());
        }

        val parts = filePath.split("/", 2);
        val groupName = parts[0];
        val path = parts[1];
        val cli = SpringUtils.getBean(FastFileStorageClient.class);

        BytesDownloadCallback callback = new BytesDownloadCallback();
        cli.downloadFile(groupName, path, callback);
        return callback.getBytes();
    }

}
