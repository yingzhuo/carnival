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

import com.github.yingzhuo.carnival.common.condition.ConditionalOnAnyResource;
import com.github.yingzhuo.carnival.common.io.ResourceOptional;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

/**
 * @author 应卓
 * @since 1.10.19
 */
@ConditionalOnAnyResource({
        "classpath:echcache.xml",
        "classpath:META-INF/echcache.xml",
        "classpath:config/echcache.xml"
})
@ConditionalOnClass(name = "org.ehcache.CacheManager")
@ConditionalOnMissingBean(name = "org.ehcache.CacheManager")
class EhCacheAutoConfig {

    @Bean
    CacheManagerFactoryBean cacheManagerFactoryBean() {
        final Resource xml = ResourceOptional.of(
                "classpath:echcache.xml",
                "classpath:META-INF/echcache.xml",
                "classpath:config/echcache.xml"
        ).get();
        return new CacheManagerFactoryBean(xml);
    }

}
