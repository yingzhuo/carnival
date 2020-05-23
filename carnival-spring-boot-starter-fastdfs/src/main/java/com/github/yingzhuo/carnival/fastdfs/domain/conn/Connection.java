/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.domain.conn;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * 表示一个客户端与服务端的连接
 *
 * @author tobato
 * @author 应卓
 * @since 1.6.10
 */
public interface Connection extends Closeable {

    /**
     * 关闭连接
     */
    public void close() throws IOException;

    /**
     * 连接是否关闭
     */
    public boolean isClosed();

    /**
     * 测试连接是否有效
     */
    public boolean isValid();

    /**
     * 获取输出流
     */
    public OutputStream getOutputStream() throws IOException;

    /**
     * 获取输入流
     */
    public InputStream getInputStream() throws IOException;

    /**
     * 获取字符集
     */
    public Charset getCharset();

}
