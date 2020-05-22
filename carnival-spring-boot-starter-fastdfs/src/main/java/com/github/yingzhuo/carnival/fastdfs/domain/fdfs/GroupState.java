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
import lombok.*;

/**
 * fastdfs中group的状态信息
 *
 * @author yuqih
 * @author tobato
 * @author 应卓
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GroupState {

    /**
     * name of this group
     */
    @Column(index = 0, max = OtherConstants.FDFS_GROUP_NAME_MAX_LEN + 1)
    private String groupName;

    /**
     * total disk storage in MB
     */
    @Column(index = 1)
    private long totalMB;

    /**
     * free disk space in MB
     */
    @Column(index = 2)
    private long freeMB;

    /**
     * trunk free space in MB
     */
    @Column(index = 3)
    private long trunkFreeMB;

    /**
     * storage server count
     */
    @Column(index = 4)
    private int storageCount;

    /**
     * storage server port
     */
    @Column(index = 5)
    private int storagePort;

    /**
     * storage server HTTP port
     */
    @Column(index = 6)
    private int storageHttpPort;

    /**
     * active storage server count
     */
    @Column(index = 7)
    private int activeCount;

    /**
     * current storage server index to upload file
     */
    @Column(index = 8)
    private int currentWriteServer;

    /**
     * store base path count of each storage server
     */
    @Column(index = 9)
    private int storePathCount;

    /**
     * sub dir count per store path
     */
    @Column(index = 10)
    private int subDirCountPerPath;

    /**
     * current trunk file id
     */
    @Column(index = 11)
    private int currentTrunkFileId;

}
