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

import java.io.IOException;
import java.io.InputStream;

/**
 * @author tobato
 * @author 应卓
 */
public class RichInputStream extends InputStream {

    private final InputStream delegate;
    private final long size;
    private long remainByteSize;

    public RichInputStream(InputStream in, long size) {
        this.delegate = in;
        this.size = size;
        this.remainByteSize = size;
    }

    @Override
    public int read() throws IOException {
        return delegate.read();
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (remainByteSize == 0) {
            return -1;
        }
        int byteSize = delegate.read(b, off, len);
        if (remainByteSize < byteSize) {
            throw new IOException("remainByteSize < byteSize!");
        }

        remainByteSize -= byteSize;
        return byteSize;
    }

    @Override
    @Deprecated
    public void close() {
        // RichInputStream 不需要关闭
        // 关闭的不由DownloadCallback负责!
    }

    public boolean isReadCompleted() {
        return remainByteSize == 0;
    }

    public long getSize() {
        return size;
    }

}
