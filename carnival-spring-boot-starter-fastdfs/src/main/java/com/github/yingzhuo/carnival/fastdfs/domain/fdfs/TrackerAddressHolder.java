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

    private InetSocketAddress address;

    private boolean available;

    private long lastUnavailableTime;

    public TrackerAddressHolder(InetSocketAddress address) {
        this.address = address;
        this.available = true;
    }

    public void setActive() {
        this.available = true;
    }

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
