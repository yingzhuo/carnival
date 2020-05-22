/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.domain.proto;

import com.github.yingzhuo.carnival.fastdfs.domain.conn.Connection;
import com.github.yingzhuo.carnival.fastdfs.exception.FastDFSIOException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * 交易命令抽象类
 *
 * @param <T>
 * @author tobato
 */
public abstract class AbstractCommand<T> implements Command<T> {

    protected Request request;

    protected Response<T> response;

    public T execute(Connection conn) {
        try {
            send(conn.getOutputStream(), conn.getCharset());
        } catch (IOException e) {
            throw new FastDFSIOException("socket io exception occurred while sending command", e);
        }

        try {
            return receive(conn.getInputStream(), conn.getCharset());
        } catch (IOException e) {
            throw new FastDFSIOException("socket io exception occurred while receive content", e);
        }

    }

    protected void send(OutputStream out, Charset charset) throws IOException {
        // 报文分为三个部分
        // 报文头
        byte[] head = request.getHeadByte(charset);
        // 交易参数
        byte[] param = request.encodeParam(charset);
        // 交易文件流
        InputStream inputFile = request.getInputFile();
        long fileSize = request.getFileSize();
        // 输出报文头
        out.write(head);
        // 输出交易参数
        if (null != param) {
            out.write(param);
        }
        // 输出文件流
        if (null != inputFile) {
            sendFileContent(inputFile, fileSize, out);
        }
    }

    protected T receive(InputStream in, Charset charset) throws IOException {
        // 解析报文头
        ProtoHead head = ProtoHead.createFromInputStream(in);
        // 校验报文头
        head.validateResponseHead();
        // 解析报文体
        return response.decode(head, in, charset);
    }

    protected void sendFileContent(InputStream ins, long size, OutputStream ous) throws IOException {
        long remainBytes = size;
        byte[] buff = new byte[256 * 1024];
        int bytes;
        while (remainBytes > 0) {
            if ((bytes = ins.read(buff, 0, remainBytes > buff.length ? buff.length : (int) remainBytes)) < 0) {
                throw new IOException("the end of the stream has been reached. not match the expected size.");
            }

            ous.write(buff, 0, bytes);
            remainBytes -= bytes;
        }
    }

}
