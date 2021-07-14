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

import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 * @since 1.9.11
 */
public class ForkPlatformTransactionManager implements PlatformTransactionManager, InitializingBean {

    public static ForkPlatformTransactionManagerBuilder builder() {
        return new ForkPlatformTransactionManagerBuilder();
    }

    private final Map<String, PlatformTransactionManager> map = new HashMap<>();
    private final Lookup lookup;
    private PlatformTransactionManager defaultTxManager;
    private String defaultTxManagerName;

    public ForkPlatformTransactionManager(String defaultTxManagerName) {
        this.lookup = new Lookup(defaultTxManagerName);
    }

    public Lookup getLookup() {
        return lookup;
    }

    void add(String name, PlatformTransactionManager txManager) {
        this.map.put(name, txManager);
    }

    public int size() {
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    // ----------------------------------------------------------------------------------------------------------------

    @Override
    public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
        return current().getTransaction(definition);
    }

    @Override
    public void commit(TransactionStatus status) throws TransactionException {
        current().commit(status);
    }

    @Override
    public void rollback(TransactionStatus status) throws TransactionException {
        current().rollback(status);
    }

    private PlatformTransactionManager current() {
        if (map.size() == 1) {
            return map.values().iterator().next();
        }

        PlatformTransactionManager txManager = map.getOrDefault(lookup.holder.get(), defaultTxManager);
        Assert.notNull(txManager, "no tx-manager found");
        return txManager;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.defaultTxManager != null) {
            this.defaultTxManager = map.get(defaultTxManagerName);
        }
    }

    // ----------------------------------------------------------------------------------------------------------------

    public static final class Lookup {
        private final ThreadLocal<String> holder;
        private final String defaultTxManagerName;

        public Lookup(String defaultTxManagerName) {
            this.defaultTxManagerName = defaultTxManagerName;
            this.holder = ThreadLocal.withInitial(() -> defaultTxManagerName);
        }

        public void set(String dataSourceName) {
            holder.set(dataSourceName);
        }

        public void reset() {
            holder.set(defaultTxManagerName);
        }
    }

}
