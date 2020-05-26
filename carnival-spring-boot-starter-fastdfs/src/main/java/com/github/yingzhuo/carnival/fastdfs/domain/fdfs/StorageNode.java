/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.domain.fdfs;

import com.github.yingzhuo.carnival.fastdfs.domain.proto.OtherConstants;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.mapper.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.net.InetSocketAddress;

/**
 * 向tracker请求上传、下载文件或其他文件请求时，tracker返回的文件storage节点的信息
 *
 * @author yuqih
 * @author 应卓
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class StorageNode {

    @Column(index = 0, max = OtherConstants.FDFS_GROUP_NAME_MAX_LEN)
    private String groupName;

    @Column(index = 1, max = OtherConstants.FDFS_IP_ADDR_SIZE - 1)
    private String ip;

    @Column(index = 2)
    private int port;

    @Column(index = 3)
    private byte storeIndex;

    public StorageNode(String ip, int port, byte storeIndex) {
        this.ip = ip;
        this.port = port;
        this.storeIndex = storeIndex;
    }

    public InetSocketAddress getInetSocketAddress() {
        return new InetSocketAddress(ip, port);
    }

}
