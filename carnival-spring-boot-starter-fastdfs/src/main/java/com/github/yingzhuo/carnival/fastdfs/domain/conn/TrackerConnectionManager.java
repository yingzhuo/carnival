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

import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.TrackerLocator;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.Command;
import com.github.yingzhuo.carnival.fastdfs.exception.FastDfsConnectException;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * 管理TrackerClient连接池分配
 *
 * @author tobato
 * @author 应卓
 */
public class TrackerConnectionManager extends AbstractConnectionManager {

    private final TrackerLocator locator;

    public TrackerConnectionManager(ConnectionPool pool, List<String> trackerList) {
        super(pool);
        this.locator = new TrackerLocator(trackerList);
    }

    /**
     * 获取连接并执行交易
     */
    public <T> T executeTrackerCommand(Command<T> command) {
        Connection conn;
        InetSocketAddress address = null;
        try {
            address = locator.getTrackerAddress();
            conn = getConnection(address);
            locator.setActive(address);
        } catch (FastDfsConnectException e) {
            locator.setInActive(address);
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unable to borrow buffer from pool", e);
        }
        return doExecute(address, conn, command);
    }

}
