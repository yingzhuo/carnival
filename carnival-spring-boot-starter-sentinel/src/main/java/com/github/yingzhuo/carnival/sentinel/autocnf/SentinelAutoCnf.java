/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.sentinel.autocnf;

import com.github.yingzhuo.carnival.sentinel.EnableSentinel;
import com.github.yingzhuo.carnival.sentinel.Sentinel;
import com.github.yingzhuo.carnival.sentinel.SentinelConfigurer;
import com.github.yingzhuo.carnival.sentinel.config.SentinelConfig;
import com.github.yingzhuo.carnival.sentinel.web.SentinelInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
public class SentinelAutoCnf implements WebMvcConfigurer {

    @Autowired
    private SentinelConfigurer configurer;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<SentinelConfig> configs = new LinkedList<>();
        configurer.addSentinelConfigs(configs);

        int order = EnableSentinel.ImportSelector.getConfig("interceptorOrder", Integer.class);

        if (!configs.isEmpty()) {
            Map<String, Sentinel> map = new TreeMap<>();
            configs.forEach(c -> map.put(c.getKey(), c.getValue()));
            registry.addInterceptor(new SentinelInterceptor(stringRedisTemplate, map)).addPathPatterns("/", "/**").order(order);
        }
    }

}
