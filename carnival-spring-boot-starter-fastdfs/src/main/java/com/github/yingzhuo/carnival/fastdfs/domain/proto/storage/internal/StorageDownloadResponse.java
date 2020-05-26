/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.domain.proto.storage.internal;

import com.github.yingzhuo.carnival.fastdfs.domain.proto.Response;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.storage.DownloadCallback;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.storage.RichInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * 文件下载结果
 *
 * @author tobato
 * @author 应卓
 */
public class StorageDownloadResponse<T> extends Response<T> {

    private DownloadCallback<T> callback;

    public StorageDownloadResponse(DownloadCallback<T> callback) {
        this.callback = callback;
    }

    @Override
    public T decodeContent(InputStream in, Charset charset) throws IOException {
        // TODO: 考虑RichInputStream 是否真的有必要
        RichInputStream input = new RichInputStream(in, getContentLength());
        return callback.recv(input);
    }

}
