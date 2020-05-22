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

import com.github.yingzhuo.carnival.fastdfs.domain.proto.mapper.BytesUtils;
import com.github.yingzhuo.carnival.fastdfs.exception.FastDFSServerException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;

/**
 * @author tobato
 */
@Getter
@Setter
@ToString
public class ProtoHead implements Serializable {

    /**
     * 报文长度
     */
    private static final int HEAD_LENGTH = OtherConstants.FDFS_PROTO_PKG_LEN_SIZE + 2;

    /**
     * 报文内容长度1-7位
     */
    private long contentLength = 0;

    /**
     * 报文类型8位
     */
    private byte cmd;

    /**
     * 处理状态9位
     */
    private byte status = (byte) 0;

    public ProtoHead(byte cmd) {
        this.cmd = cmd;
    }

    public ProtoHead(long contentLength, byte cmd, byte status) {
        this.contentLength = contentLength;
        this.cmd = cmd;
        this.status = status;
    }

    public static ProtoHead createFromInputStream(InputStream ins) throws IOException {

        byte[] header = new byte[HEAD_LENGTH];
        int bytes;
        // 读取HEAD_LENGTH长度的输入流
        if ((bytes = ins.read(header)) != header.length) {
            throw new IOException("recv package size " + bytes + " != " + header.length);
        }
        long returnContentLength = BytesUtils.buff2long(header, 0);
        byte returnCmd = header[OtherConstants.PROTO_HEADER_CMD_INDEX];
        byte returnStatus = header[OtherConstants.PROTO_HEADER_STATUS_INDEX];
        // 返回解析出来的ProtoHead
        return new ProtoHead(returnContentLength, returnCmd, returnStatus);

    }

    public byte[] toBytes() {
        byte[] header;
        byte[] hex_len;

        header = new byte[HEAD_LENGTH];
        Arrays.fill(header, (byte) 0);
        hex_len = BytesUtils.long2buff(contentLength);
        System.arraycopy(hex_len, 0, header, 0, hex_len.length);
        header[OtherConstants.PROTO_HEADER_CMD_INDEX] = cmd;
        header[OtherConstants.PROTO_HEADER_STATUS_INDEX] = status;
        return header;
    }

    public void validateResponseHead() throws IOException {
        // 检查是否是正确反馈报文
        if (cmd != CmdConstants.FDFS_PROTO_CMD_RESP) {
            throw new IOException(
                    "recv cmd: " + cmd + " is not correct, expect cmd: " + CmdConstants.FDFS_PROTO_CMD_RESP);
        }
        // 获取处理错误状态
        if (status != 0) {
            throw FastDFSServerException.byCode(status);
        }

        if (contentLength < 0) {
            throw new IOException("recv body length: " + contentLength + " < 0!");
        }
    }

}
