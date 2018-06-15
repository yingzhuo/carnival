/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.datasource;

import com.github.yingzhuo.carnival.common.IntSized;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * @author 应卓
 */
public class CompositeDataSource implements DataSource, IntSized, Iterable<Map.Entry<String, DataSource>> {

    private final Remote remote = new Remote();
    private final Map<String, DataSource> cachedDataSources = new TreeMap<>();
    private final String defaultEffectiveDataSourceName;

    public CompositeDataSource(String defaultEffectiveDataSourceName) {

        if (defaultEffectiveDataSourceName == null) {
            throw new NullPointerException();
        }

        this.defaultEffectiveDataSourceName = defaultEffectiveDataSourceName;
    }

    public CompositeDataSource add(String name, DataSource dataSource) {
        cachedDataSources.put(name, dataSource);
        return this;
    }

    public CompositeDataSource addAll(Map<String, DataSource> dataSources) {
        cachedDataSources.putAll(dataSources);
        return this;
    }

    public Remote getRemote() {
        return this.remote;
    }

    @Override
    public Integer size() {
        return cachedDataSources.size();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return effective().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return effective().getConnection(username, password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return effective().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return effective().isWrapperFor(iface);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return effective().getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        effective().setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        effective().setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return effective().getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return effective().getParentLogger();
    }

    @Override
    public Iterator<Map.Entry<String, DataSource>> iterator() {
        return cachedDataSources.entrySet().iterator();
    }

    private DataSource effective() {
        String name = getRemote().nameHolder.get();

        if (name == null) {
            name = this.defaultEffectiveDataSourceName;
        }

        DataSource dataSource = cachedDataSources.get(name);

        if (dataSource == null) {
            throw new IllegalStateException("Cannot find datasource for name");
        }

        return dataSource;
    }

    // ---------------------------------------------------------------------------------------------------------------

    public class Remote {
        private final ThreadLocal<String> nameHolder = ThreadLocal.withInitial(() -> null);

        public void setName(String name) {
            nameHolder.set(name);
        }

        public String getName() {
            return nameHolder.get();
        }
    }
}
