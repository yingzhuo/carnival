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
import com.github.yingzhuo.carnival.fastdfs.properties.WebProperties;
import com.github.yingzhuo.carnival.spring.SpringUtils;
import lombok.val;
import lombok.var;

import java.io.InputStream;
import java.util.Collections;

/**
 * @author 应卓
 * @since 1.6.10
 */
public final class FastDFS {

    private FastDFS() {
    }

    public static String upload(InputStream in, long fileSize, String fileExtName) {

        var prefix = SpringUtils.getBean(WebProperties.class).getUrl();
        if (prefix == null) {
            prefix = "";
        }

        val client = SpringUtils.getBean(FastFileStorageClient.class);
        val data = client.uploadFile(in, fileSize, fileExtName, Collections.emptySet());
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

        val client = SpringUtils.getBean(FastFileStorageClient.class);
        client.deleteFile(filePath);
    }

}
