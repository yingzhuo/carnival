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

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 * @since 1.6.0
 */
public class ForkDataSourceBuilder {

    private final Map<String, DataSource> map = new HashMap<>();
    private String defaultDataSourceName = null;

    ForkDataSourceBuilder() {
    }

    public ForkDataSourceBuilder defaultDataSource(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("default datasource name is null or empty.");
        }
        this.defaultDataSourceName = name;
        return this;
    }

    public ForkDataSourceBuilder dataSource(String name, DataSource dataSource) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("datasource name is null or empty.");
        }
        if (dataSource == null) {
            throw new IllegalArgumentException("datasource is null.");
        }
        map.put(name, dataSource);
        return this;
    }

    public ForkDataSource build() {

        if (defaultDataSourceName == null) {
            throw new IllegalStateException("default datasource name is null.");
        }

        if (map.isEmpty()) {
            throw new IllegalStateException("no datasource is set.");
        }

        if (!map.containsKey(defaultDataSourceName)) {
            throw new IllegalStateException("wrong default datasource name.");
        }

        ForkDataSource dataSource = new ForkDataSource(this.defaultDataSourceName);
        for (String name : map.keySet()) {
            DataSource ds = map.get(name);
            dataSource.add(name, ds);
        }
        return dataSource;
    }

}
