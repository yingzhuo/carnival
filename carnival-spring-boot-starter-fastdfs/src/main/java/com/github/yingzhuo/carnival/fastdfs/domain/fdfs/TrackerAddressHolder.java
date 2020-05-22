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

import java.net.InetSocketAddress;

/**
 * 管理TrackerAddress当前状态
 *
 * @author tobato
 */
public class TrackerAddressHolder {

    /**
     * 连接地址
     */
    private InetSocketAddress address;

    /**
     * 当前是否有效
     */
    private boolean available;

    /**
     * 上次无效时间
     */
    private long lastUnavailableTime;

    public TrackerAddressHolder(InetSocketAddress address) {
        this.address = address;
        this.available = true;
    }

    /**
     * 有效
     */
    public void setActive() {
        this.available = true;
    }

    /**
     * 无效
     */
    public void setInActive() {
        this.available = false;
        this.lastUnavailableTime = System.currentTimeMillis();
    }

    public boolean isAvailable() {
        return available;
    }

    public long getLastUnavailableTime() {
        return lastUnavailableTime;
    }

    /**
     * 是否可以尝试连接
     */
    public boolean canTryToConnect(int retryAfterSeconds) {
        if (this.available) {
            return true;
        }
        return (System.currentTimeMillis() - lastUnavailableTime) > retryAfterSeconds * 1000;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

}
