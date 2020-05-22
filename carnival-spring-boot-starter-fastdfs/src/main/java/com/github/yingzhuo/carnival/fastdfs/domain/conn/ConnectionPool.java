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

import com.github.yingzhuo.carnival.fastdfs.properties.PoolProperties;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;

import java.net.InetSocketAddress;

/**
 * 连接池
 *
 * @author tobato
 * @author 应卓
 */
public class ConnectionPool extends GenericKeyedObjectPool<InetSocketAddress, Connection> {

    public ConnectionPool(ConnectionFactory factory, PoolProperties config) {
        super(factory, config);
    }

}
