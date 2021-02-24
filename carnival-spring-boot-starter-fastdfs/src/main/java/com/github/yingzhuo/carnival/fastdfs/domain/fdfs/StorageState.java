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
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * fastdfs中storage节点的状态信息
 *
 * @author yuqih
 * @author 应卓
 */
@Getter
@Setter
@ToString
public class StorageState {

    /**
     * 状态代码
     */
    @Column(index = 0)
    private byte status;
    /**
     * id
     */
    @Column(index = 1, max = OtherConstants.FDFS_STORAGE_ID_MAX_SIZE)
    private String id;

    /**
     * ip地址
     */
    @Column(index = 2, max = OtherConstants.FDFS_IP_ADDR_SIZE)
    private String ipAddr;

    /**
     * domain
     */
    @Column(index = 3, max = OtherConstants.FDFS_DOMAIN_NAME_MAX_SIZE)
    private String domainName; // http domain name

    /**
     * 源ip地址
     */
    @Column(index = 4, max = OtherConstants.FDFS_IP_ADDR_SIZE)
    private String srcIpAddr;

    /**
     * version
     */
    @Column(index = 5, max = OtherConstants.FDFS_VERSION_SIZE)
    private String version;

    /**
     * 存储加入时间
     */
    @Column(index = 6)
    private Date joinTime; // storage join timestamp (create timestamp)

    /**
     * 存储更新时间
     */
    @Column(index = 7)
    private Date upTime; // storage service started timestamp

    /**
     * 存储总容量
     */
    @Column(index = 8)
    private long totalMB; // total disk storage in MB

    /**
     * 空闲存储
     */
    @Column(index = 9)
    private long freeMB; // free disk storage in MB

    /**
     * 文件上传权重
     */
    @Column(index = 10)
    private int uploadPriority; // upload priority

    /**
     * 存储路径数
     */
    @Column(index = 11)
    private int storePathCount; // store base path count of each storage

    // server
    /**
     * 存储路径子目录数
     */
    @Column(index = 12)
    private int subDirCountPerPath;

    /**
     * 当前写路径
     */
    @Column(index = 13)
    private int currentWritePath; // current write path index

    /**
     * 存储端口
     */
    @Column(index = 14)
    private int storagePort;

    /**
     * 存储http端口
     */
    @Column(index = 15)
    private int storageHttpPort; // storage http server port

    @Column(index = 16, max = OtherConstants.FDFS_PROTO_CONNECTION_LEN)
    private int connectionAllocCount;

    @Column(index = 17, max = OtherConstants.FDFS_PROTO_CONNECTION_LEN)
    private int connectionCurrentCount;

    @Column(index = 18, max = OtherConstants.FDFS_PROTO_CONNECTION_LEN)
    private int connectionMaxCount;

    /**
     * 总上传文件数
     */
    @Column(index = 19)
    private long totalUploadCount;

    /**
     * 成功上传文件数
     */
    @Column(index = 20)
    private long successUploadCount;

    /**
     * 合并存储文件数
     */
    @Column(index = 21)
    private long totalAppendCount;

    /**
     * 成功合并文件数
     */
    @Column(index = 22)
    private long successAppendCount;

    /**
     * 文件修改数
     */
    @Column(index = 23)
    private long totalModifyCount;

    /**
     * 文件成功修改数
     */
    @Column(index = 24)
    private long successModifyCount;

    /**
     * 总清除数
     */
    @Column(index = 25)
    private long totalTruncateCount;

    /**
     * 成功清除数
     */
    @Column(index = 26)
    private long successTruncateCount;

    /**
     * 总设置标签数
     */
    @Column(index = 27)
    private long totalSetMetaCount;

    /**
     * 成功设置标签数
     */
    @Column(index = 28)
    private long successSetMetaCount;

    /**
     * 总删除文件数
     */
    @Column(index = 29)
    private long totalDeleteCount;

    /**
     * 成功删除文件数
     */
    @Column(index = 30)
    private long successDeleteCount;

    /**
     * 总下载量
     */
    @Column(index = 31)
    private long totalDownloadCount;

    /**
     * 成功下载量
     */
    @Column(index = 32)
    private long successDownloadCount;

    /**
     * 总获取标签数
     */
    @Column(index = 33)
    private long totalGetMetaCount;

    /**
     * 成功获取标签数
     */
    @Column(index = 34)
    private long successGetMetaCount;

    /**
     * 总创建链接数
     */
    @Column(index = 35)
    private long totalCreateLinkCount;

    /**
     * 成功创建链接数
     */
    @Column(index = 36)
    private long successCreateLinkCount;

    /**
     * 总删除链接数
     */
    @Column(index = 37)
    private long totalDeleteLinkCount;

    /**
     * 成功删除链接数
     */
    @Column(index = 38)
    private long successDeleteLinkCount;

    /**
     * 总上传数据量
     */
    @Column(index = 39)
    private long totalUploadBytes;

    /**
     * 成功上传数据量
     */
    @Column(index = 40)
    private long successUploadBytes;

    /**
     * 合并数据量
     */
    @Column(index = 41)
    private long totalAppendBytes;

    /**
     * 成功合并数据量
     */
    @Column(index = 42)
    private long successAppendBytes;

    /**
     * 修改数据量
     */
    @Column(index = 43)
    private long totalModifyBytes;

    /**
     * 成功修改数据量
     */
    @Column(index = 44)
    private long successModifyBytes;

    /**
     * 下载数据量
     */
    @Column(index = 45)
    private long totalDownloadLoadBytes;

    /**
     * 成功下载数据量
     */
    @Column(index = 46)
    private long successDownloadLoadBytes;

    /**
     * 同步数据量
     */
    @Column(index = 47)
    private long totalSyncInBytes;

    /**
     * 成功同步数据量
     */
    @Column(index = 48)
    private long successSyncInBytes;

    /**
     * 同步输出数据量
     */
    @Column(index = 49)
    private long totalSyncOutBytes;

    /**
     * 成功同步输出数据量
     */
    @Column(index = 50)
    private long successSyncOutBytes;

    /**
     * 打开文件数量
     */
    @Column(index = 51)
    private long totalFileOpenCount;

    /**
     * 成功打开文件数量
     */
    @Column(index = 52)
    private long successFileOpenCount;

    /**
     * 文件读取数量
     */
    @Column(index = 53)
    private long totalFileReadCount;

    /**
     * 文件成功读取数量
     */
    @Column(index = 54)
    private long successFileReadCount;

    /**
     * 文件写数量
     */
    @Column(index = 56)
    private long totalFileWriteCount;

    /**
     * 文件成功写数量
     */
    @Column(index = 57)
    private long successFileWriteCount;

    /**
     * 最后上传时间
     */
    @Column(index = 58)
    private Date lastSourceUpdate;

    /**
     * 最后同步时间
     */
    @Column(index = 59)
    private Date lastSyncUpdate;

    /**
     * 最后同步时间戳
     */
    @Column(index = 60)
    private Date lastSyncedTimestamp;

    /**
     * 最后心跳时间
     */
    @Column(index = 61)
    private Date lastHeartBeatTime;

    /**
     * 是否trunk服务器
     */
    @Column(index = 62)
    private boolean isTrunkServer;

}
