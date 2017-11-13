/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.refuse.autoconfig;

import com.github.yingzhuo.carnival.refuse.RefuseConfigLoader;
import com.github.yingzhuo.carnival.refuse.RefuseListener;
import com.github.yingzhuo.carnival.refuse.impl.AlawaysEmptyRefuseConfigLoader;
import com.github.yingzhuo.carnival.refuse.impl.NopRefuseListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
@ConditionalOnWebApplication
public class RefuseBeanConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RefuseConfigLoader refuseConfigLoader() {
        return new AlawaysEmptyRefuseConfigLoader();
    }

    @Bean
    @ConditionalOnMissingBean
    public RefuseListener refuseListener() {
        return new NopRefuseListener();
    }

}
