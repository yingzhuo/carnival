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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * @author 应卓
 */
public class ForkDataSource implements DataSource, IntSized, Iterable<Map.Entry<String, DataSource>> {

    private final Remote remote = new Remote();
    private final Map<String, DataSource> cachedDataSources = new TreeMap<>();
    private String defaultDataSourceName;

    public ForkDataSource() {
        this(null);
    }

    public ForkDataSource(String defaultDataSourceName) {
        this.defaultDataSourceName = defaultDataSourceName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public ForkDataSource add(String name, DataSource dataSource) {
        cachedDataSources.put(name, dataSource);
        return this;
    }

    public ForkDataSource addAll(Map<String, DataSource> dataSources) {
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
    public int getLoginTimeout() throws SQLException {
        return effective().getLoginTimeout();
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        effective().setLoginTimeout(seconds);
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

        if (name == null && defaultDataSourceName != null) {
            name = this.defaultDataSourceName;
        }

        DataSource dataSource = cachedDataSources.get(name);

        if (dataSource == null) {
            throw new IllegalStateException("Cannot find datasource for name");
        }

        return dataSource;
    }

    // ---------------------------------------------------------------------------------------------------------------

    public static class Builder {

        private Map<String, DataSource> dataSourceMap = new HashMap<>();
        private String defaultDataSourceName;

        private Builder() {
            super();
        }

        public Builder defaultDataSourceName(String defaultDataSourceName) {
            this.defaultDataSourceName = defaultDataSourceName;
            return this;
        }

        public Builder put(String name, DataSource dataSource) {
            dataSourceMap.put(name, dataSource);
            return this;
        }

        public ForkDataSource build() {
            ForkDataSource bean = new ForkDataSource();
            bean.defaultDataSourceName = this.defaultDataSourceName;
            bean.cachedDataSources.putAll(this.dataSourceMap);
            return bean;
        }
    }

    // ---------------------------------------------------------------------------------------------------------------

    public class Remote {

        private final ThreadLocal<String> nameHolder = ThreadLocal.withInitial(() -> null);

        public String getName() {
            return nameHolder.get();
        }

        public void setName(String name) {
            nameHolder.set(name);
        }
    }
}
