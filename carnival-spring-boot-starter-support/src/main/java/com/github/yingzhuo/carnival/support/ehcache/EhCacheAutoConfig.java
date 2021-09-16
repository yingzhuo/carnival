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

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

/**
 * @author 应卓
 * @since 1.10.19
 */
@EnableConfigurationProperties(EhCacheAutoConfig.Props.class)
@ConditionalOnClass(name = "org.ehcache.CacheManager")
@ConditionalOnMissingBean(name = "org.ehcache.CacheManager")
@ConditionalOnProperty(prefix = "carnival.support.ehcache", name = "enabled", havingValue = "true", matchIfMissing = false)
class EhCacheAutoConfig {

    @Bean
    CacheManagerFactoryBean cacheManagerFactoryBean(Props props) {
        return new CacheManagerFactoryBean(props.getXmlLocation());
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "carnival.support.ehcache")
    static class Props {
        private boolean enabled = false;
        private Resource xmlLocation;
    }

}
