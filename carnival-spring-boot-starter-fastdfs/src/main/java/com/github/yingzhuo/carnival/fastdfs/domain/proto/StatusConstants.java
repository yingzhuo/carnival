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

/**
 * fastdfs协议服务端状态的常量
 *
 * @author yuqih
 */
@Deprecated
public interface StatusConstants {
    public static final byte FDFS_STORAGE_STATUS_INIT = 0;
    public static final byte FDFS_STORAGE_STATUS_WAIT_SYNC = 1;
    public static final byte FDFS_STORAGE_STATUS_SYNCING = 2;
    public static final byte FDFS_STORAGE_STATUS_IP_CHANGED = 3;
    public static final byte FDFS_STORAGE_STATUS_DELETED = 4;
    public static final byte FDFS_STORAGE_STATUS_OFFLINE = 5;
    public static final byte FDFS_STORAGE_STATUS_ONLINE = 6;
    public static final byte FDFS_STORAGE_STATUS_ACTIVE = 7;
    public static final byte FDFS_STORAGE_STATUS_NONE = 99;
}
