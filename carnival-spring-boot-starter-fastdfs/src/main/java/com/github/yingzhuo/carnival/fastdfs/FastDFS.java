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
import com.github.yingzhuo.carnival.spring.SpringUtils;
import lombok.val;

import java.io.InputStream;
import java.util.Collections;

/**
 * @author 应卓
 * @since 1.6.10
 */
public final class FastDFS {

    private FastDFS() {
    }

    public static String upload(InputStream in, String fileExtName) {
        // TODO: 把NGINX前缀考略进去
        val client = SpringUtils.getBean(FastFileStorageClient.class);
        val data = client.uploadFile(in, 0L, fileExtName, Collections.emptySet());
        return data.getFullPath();
    }

    public static void delete(String filePath) {
        // TODO: 把NGINX前缀考略进去
        val client = SpringUtils.getBean(FastFileStorageClient.class);
        client.deleteFile(filePath);
    }

}
