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
import com.github.yingzhuo.carnival.fastdfs.domain.proto.Response;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.storage.internal.StorageDeleteFileRequest;

/**
 * 文件删除命令
 *
 * @author tobato
 */
public class StorageDeleteFileCommand extends AbstractCommand<Void> {

    public StorageDeleteFileCommand(String groupName, String path) {
        this.request = new StorageDeleteFileRequest(groupName, path);
        // 输出响应
        this.response = new Response<Void>() {
            // default response
        };
    }

}
