/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mustache.autoconfig;

import com.github.yingzhuo.carnival.mustache.MustacheBean;
import com.github.yingzhuo.carnival.mustache.MustacheBeanImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @since 1.6.33
 */
public class MustacheAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public MustacheBean mustacheBean() {
        return new MustacheBeanImpl();
    }

}
