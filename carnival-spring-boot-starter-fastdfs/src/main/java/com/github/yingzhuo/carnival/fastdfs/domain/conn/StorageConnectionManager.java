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

import com.github.yingzhuo.carnival.fastdfs.domain.proto.Command;

import java.net.InetSocketAddress;

/**
 * 连接池管理
 *
 * @author tobato
 * @author 应卓
 */
public class StorageConnectionManager extends AbstractConnectionManager {

    public StorageConnectionManager(ConnectionPool pool) {
        super(pool);
    }

    /**
     * 获取连接并执行交易
     */
    public <T> T executeCommand(InetSocketAddress address, Command<T> command) {
        Connection conn = getConnection(address);
        return doExecute(address, conn, command);
    }

}
