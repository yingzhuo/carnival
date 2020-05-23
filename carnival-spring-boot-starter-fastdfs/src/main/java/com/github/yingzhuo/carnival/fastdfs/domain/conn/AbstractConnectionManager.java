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
import com.github.yingzhuo.carnival.fastdfs.exception.FastDFSException;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author 应卓
 * @since 1.6.10
 */
@Slf4j
abstract class AbstractConnectionManager {

    /**
     * 连接池
     */
    private final ConnectionPool pool;

    public AbstractConnectionManager(ConnectionPool pool) {
        this.pool = pool;
    }

    protected final <T> T doExecute(InetSocketAddress address, Connection conn, Command<T> command) {
        boolean isException = false;
        try {
            return command.execute(conn);
        } catch (FastDFSException e) {
            isException = true;
            throw e;
        } catch (Exception e) {
            isException = true;
            throw new RuntimeException("execute fdfs command error", e);
        } finally {
            if (isException) {
                removeConnect(address, conn);
            } else {
                returnConnect(address, conn);
            }
        }
    }

    /**
     * 出现例外时从连接池移除连接
     */
    private void removeConnect(InetSocketAddress address, Connection conn) {
        try {
            if (null != conn) {
                //移除pool
                pool.invalidateObject(address, conn);
            }
        } catch (Exception e) {
            log.warn("remove pooled connection error", e);
        }
    }

    /**
     * 归还连接
     */
    private void returnConnect(InetSocketAddress address, Connection conn) {
        try {
            if (null != conn) {
                pool.returnObject(address, conn);
            }
        } catch (Exception e) {
            log.error("return pooled connection error", e);
        }
    }

    /**
     * 获取连接
     */
    protected Connection getConnection(InetSocketAddress address) {
        final Connection conn;
        try {
            // 获取连接
            conn = pool.borrowObject(address);
            //dumpPoolInfo(address);
        } catch (FastDFSException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unable to borrow buffer from pool", e);
        }
        return conn;
    }

    /**
     * 打印连接池情况
     */
    public final void dumpPoolInfo(InetSocketAddress address) {

        if (log.isDebugEnabled()) {
            log.debug("===============================");
            log.debug("Address={}", address);
            log.debug("连接池最大连接数配置{}", pool.getMaxTotal());
            log.debug("每个Key最大连接数配置{}", pool.getMaxTotalPerKey());
            log.debug("每个key对应连接池最大空闲连接数{}", pool.getMaxIdlePerKey());
            log.debug("每个key对应连接池最小空闲连接数{}", pool.getMinIdlePerKey());
            log.debug("活动连接{}", pool.getNumActive(address));
            log.debug("空闲连接{}", pool.getNumIdle(address));
            log.debug("获取前测试连接状态{}", pool.getTestOnBorrow());
            log.debug("归还前测试连接状态{}", pool.getTestOnReturn());
            log.debug("空闲时测试连接状态{}", pool.getTestWhileIdle());
            log.debug("连接获取总数统计{}", pool.getBorrowedCount());
            log.debug("连接返回总数统计{}", pool.getReturnedCount());
            log.debug("连接销毁总数统计{}", pool.getDestroyedCount());
            log.debug("JmxName={}", pool.getJmxName());
            log.debug("===============================");
        }
    }

}
