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

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

/**
 * @author 应卓
 * @since 1.10.19
 */
public class CacheManagerFactoryBean implements FactoryBean<CacheManager>, InitializingBean {

    private final Resource xmlConfigFile;
    private CacheManager cacheManager;

    public CacheManagerFactoryBean(Resource xmlConfigFile) {
        this.xmlConfigFile = xmlConfigFile;
    }

    @Override
    public CacheManager getObject() {
        return cacheManager;
    }

    @Override
    public Class<?> getObjectType() {
        return CacheManager.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.cacheManager = CacheManagerBuilder.newCacheManager(new XmlConfiguration(xmlConfigFile.getURL()));
        this.cacheManager.init();
    }

}
