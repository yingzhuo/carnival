/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.support.ehcache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.springframework.beans.factory.FactoryBean;

import java.util.Objects;

/**
 * @author 应卓
 * @since 1.10.19
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractCacheFactoryBean implements FactoryBean<Cache> {

    protected final CacheManager cacheManager;

    public AbstractCacheFactoryBean(CacheManager cacheManager) {
        this.cacheManager = Objects.requireNonNull(cacheManager);
    }

    @Override
    public abstract Cache getObject() throws Exception;

    @Override
    public final Class<?> getObjectType() {
        return Cache.class;
    }

}
