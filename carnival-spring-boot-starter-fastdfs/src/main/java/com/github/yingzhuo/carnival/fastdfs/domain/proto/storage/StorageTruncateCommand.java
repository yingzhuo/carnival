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
import com.github.yingzhuo.carnival.fastdfs.domain.proto.storage.internal.StorageTruncateRequest;

/**
 * 文件Truncate命令
 *
 * @author tobato
 */
public class StorageTruncateCommand extends AbstractCommand<Void> {

    public StorageTruncateCommand(String path, long fileSize) {
        this.request = new StorageTruncateRequest(path, fileSize);
        // 输出响应
        this.response = new Response<Void>() {
            // default response
        };
    }

}
