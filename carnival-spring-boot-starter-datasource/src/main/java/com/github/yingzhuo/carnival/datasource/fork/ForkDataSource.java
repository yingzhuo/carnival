/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.datasource.fork;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author 应卓
 * @since 1.3.7
 */
public class ForkDataSource implements DataSource, InitializingBean {

    private final Map<String, DataSource> dataSourceMap = new HashMap<>();
    private DataSource defaultDataSource;
    private String defaultDataSourceName;
    private final Lookup lookup;

    public static ForkDataSourceBuilder builder() {
        return new ForkDataSourceBuilder();
    }

    ForkDataSource(String defaultDataSourceName) {
        this.lookup = new Lookup(defaultDataSourceName);
    }

    void addDataSource(String dataSourceName, DataSource dataSource) {
        this.dataSourceMap.put(dataSourceName, dataSource);
    }

    public Lookup getLookup() {
        return this.lookup;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return current().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return current().getConnection(username, password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return current().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return current().isWrapperFor(iface);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return current().getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        current().setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        current().setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return current().getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return current().getParentLogger();
    }

    private DataSource current() {
        if (dataSourceMap.size() == 1) {
            return dataSourceMap.values().iterator().next();
        }

        DataSource dataSource = dataSourceMap.getOrDefault(lookup.holder.get(), defaultDataSource);
        Assert.notNull(dataSource, "no datasource found");
        return dataSource;
    }

    @Override
    public void afterPropertiesSet() {
        if (defaultDataSourceName != null) {
            this.defaultDataSource = dataSourceMap.get(defaultDataSourceName);
        }
    }

    // ----------------------------------------------------------------------------------------------------------------

    public static final class Lookup {
        private final ThreadLocal<String> holder;
        private final String defaultDataSourceName;

        public Lookup(String defaultDataSourceName) {
            this.defaultDataSourceName = defaultDataSourceName;
            this.holder = ThreadLocal.withInitial(() -> defaultDataSourceName);
        }

        public void set(String dataSourceName) {
            holder.set(dataSourceName);
        }

        public void reset() {
            holder.set(defaultDataSourceName);
        }
    }
}
