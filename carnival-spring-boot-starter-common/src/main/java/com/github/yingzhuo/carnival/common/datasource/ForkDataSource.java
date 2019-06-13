/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.datasource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 * @since 1.0.2
 */
public class ForkDataSource extends AbstractRoutingDataSource implements DataSource, InitializingBean {

    public static Builder builder() {
        return new Builder();
    }

    private static final ThreadLocal<String> KEY_HOLDER = new InheritableThreadLocal<>();

    public static void setDataSourceKey(String dataSourceKey) {
        KEY_HOLDER.set(dataSourceKey);
    }

    @Deprecated
    public static void clearDataSourceKey() {
        KEY_HOLDER.remove();
    }

    public static void reset() {
        KEY_HOLDER.remove();
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return KEY_HOLDER.get();
    }

    public static class Builder {

        private Builder() {
        }

        private final Map<Object, Object> dataSourceMap = new HashMap<>();
        private String defaultKey;

        public Builder addDataSource(String key, DataSource dataSource) {
            if (defaultKey == null) {
                this.defaultKey = key;
            }
            dataSourceMap.put(key, dataSource);
            return this;
        }

        public Builder setDefault(String key) {
            this.defaultKey = key;
            return this;
        }

        public ForkDataSource build() {
            if (dataSourceMap.isEmpty()) {
                throw new IllegalArgumentException("You should add at least one datasource.");
            }

            ForkDataSource dataSource = new ForkDataSource();
            dataSource.setLenientFallback(true);
            dataSource.setTargetDataSources(dataSourceMap);
            dataSource.setDefaultTargetDataSource(dataSourceMap.get(defaultKey));
            return dataSource;
        }
    }
}
