/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.database.tx;

import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 * @since 1.9.11
 */
public class ForkPlatformTransactionManagerBuilder {

    private final Map<String, PlatformTransactionManager> map = new HashMap<>();
    private String defaultTxManagerName = null;

    ForkPlatformTransactionManagerBuilder() {
    }

    public ForkPlatformTransactionManagerBuilder defaultTxManagerName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("default tx-manager name is null or empty.");
        }
        this.defaultTxManagerName = name;
        return this;
    }

    public ForkPlatformTransactionManagerBuilder txManager(String name, PlatformTransactionManager txManager) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("datasource name is null or empty.");
        }
        if (txManager == null) {
            throw new IllegalArgumentException("datasource is null.");
        }
        map.put(name, txManager);
        return this;
    }

    public ForkPlatformTransactionManager build() {

        if (defaultTxManagerName == null) {
            throw new IllegalStateException("default tx-manager name is null.");
        }

        if (map.isEmpty()) {
            throw new IllegalStateException("no tx-manager is set.");
        }

        if (!map.containsKey(defaultTxManagerName)) {
            throw new IllegalStateException("wrong default tx-manager name.");
        }

        ForkPlatformTransactionManager txManager = new ForkPlatformTransactionManager(this.defaultTxManagerName);
        for (String name : map.keySet()) {
            PlatformTransactionManager manager = map.get(name);
            txManager.add(name, manager);
        }
        return txManager;
    }

}
