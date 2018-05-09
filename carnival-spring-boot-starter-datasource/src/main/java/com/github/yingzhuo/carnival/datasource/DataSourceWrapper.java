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

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.*;
import java.util.logging.Logger;

public class DataSourceWrapper implements DataSource {

    private final Map<String, DataSource> dataSourceMap = new HashMap<>();
    private String defaultName;

    public DataSourceWrapper() {
        this(null);
    }

    public DataSourceWrapper(String defaultName) {
        this.defaultName = defaultName;
    }

    public DataSourceWrapper add(NamedDataSource namedDataSource) {
        Objects.requireNonNull(namedDataSource);
        dataSourceMap.put(namedDataSource.getName(), namedDataSource);
        return this;
    }

    public DataSourceWrapper add(String name, DataSource dataSource) {
        return add(NamedDataSource.newInstance(name, dataSource));
    }

    public Set<String> getDataSourceNameSet() {
        return dataSourceMap.keySet();
    }

    public boolean isEmpty() {
        return dataSourceMap.isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return _getDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return _getDataSource().getConnection(username, password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return _getDataSource().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return _getDataSource().isWrapperFor(iface);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return _getDataSource().getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        _getDataSource().setLogWriter(out);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return _getDataSource().getLoginTimeout();
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        _getDataSource().setLoginTimeout(seconds);
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return _getDataSource().getParentLogger();
    }

    private DataSource _getDataSource() {
        DataSource dataSource = Optional.ofNullable(dataSourceMap.get(Switch.getName()))
                .orElse(dataSourceMap.get(defaultName));

        return Objects.requireNonNull(dataSource);
    }

    public static final class Switch {

        private static final ThreadLocal<String> HOLDER = ThreadLocal.withInitial(() -> null);

        private Switch() {
        }

        public static String getName() {
            return HOLDER.get();
        }

        public static void setName(String name) {
            HOLDER.set(name);
        }
    }
}
