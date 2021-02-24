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

import org.apache.commons.io.IOUtils;

import java.io.IOException;

/**
 * @author 应卓
 * @since 1.6.11
 */
public class BytesDownloadCallback implements DownloadCallback<byte[]> {

    private byte[] bytes;

    @Override
    public byte[] done(RichInputStream ins) throws IOException {
        bytes = IOUtils.toByteArray(ins);
        return bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

}
