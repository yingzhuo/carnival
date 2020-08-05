package com.github.yingzhuo.carnival.mustache.autoconfig;

import com.github.yingzhuo.carnival.mustache.MustacheBean;
import com.github.yingzhuo.carnival.mustache.MustacheBeanImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public class MustacheAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public MustacheBean mustacheBean() {
        return new MustacheBeanImpl();
    }

}
