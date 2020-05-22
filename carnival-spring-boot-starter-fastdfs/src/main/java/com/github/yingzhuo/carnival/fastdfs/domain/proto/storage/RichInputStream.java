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
 * FdfsInputStream包装类
 *
 * @author tobato
 */
public class RichInputStream extends InputStream {

    private final InputStream ins;
    private final long size;
    private long remainByteSize;

    public RichInputStream(InputStream ins, long size) {
        this.ins = ins;
        this.size = size;
        remainByteSize = size;
    }

    @Override
    public int read() throws IOException {
        return ins.read();
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (remainByteSize == 0) {
            return -1;
        }
        int byteSize = ins.read(b, off, len);
        if (remainByteSize < byteSize) {
            throw new IOException("协议长度" + size + "与实际长度不符");
        }

        remainByteSize -= byteSize;
        return byteSize;
    }

    @Override
    public void close() throws IOException {
        // do nothing
    }

    /**
     * 是否已完成读取
     */
    public boolean isReadCompleted() {
        return remainByteSize == 0;
    }

}
