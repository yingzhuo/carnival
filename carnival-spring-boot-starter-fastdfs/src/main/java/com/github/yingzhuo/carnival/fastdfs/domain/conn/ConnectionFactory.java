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

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * 连接工厂
 *
 * @author tobato
 * @author 应卓
 * @since 1.6.10
 */
public class ConnectionFactory extends BaseKeyedPooledObjectFactory<InetSocketAddress, Connection> {

    /**
     * 读取时间
     */
    private final int soTimeout;

    /**
     * 连接超时时间
     */
    private final int connectTimeout;

    /**
     * 字符集
     */
    private final Charset charset;

    public ConnectionFactory(int soTimeout, int connectTimeout, Charset charset) {
        this.soTimeout = soTimeout;
        this.connectTimeout = connectTimeout;
        this.charset = charset;
    }

    /**
     * 创建连接
     */
    @Override
    public Connection create(InetSocketAddress address) {
        return new ConnectionImpl(address, soTimeout, connectTimeout, charset);
    }

    /**
     * 将对象池化pooledObject
     */
    @Override
    public PooledObject<Connection> wrap(Connection conn) {
        return new PooledConnection(conn);
    }

    /**
     * 从池中移出
     */
    @Override
    public void destroyObject(InetSocketAddress key, PooledObject<Connection> p) throws Exception {
        p.getObject().close();
    }

    /**
     * 验证池中对象是否可用
     */
    @Override
    public boolean validateObject(InetSocketAddress key, PooledObject<Connection> p) {
        return p.getObject().isValid();
    }

    // --------------------------------------------------------------------------------------------------------------

    private static class PooledConnection extends DefaultPooledObject<Connection> {
        public PooledConnection(Connection object) {
            super(object);
        }
    }

}
