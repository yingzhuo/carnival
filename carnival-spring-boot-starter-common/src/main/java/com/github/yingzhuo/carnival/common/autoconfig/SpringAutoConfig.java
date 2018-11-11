/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.autoconfig;

import com.github.yingzhuo.carnival.spring.SpringIdFactoryBean;
import com.github.yingzhuo.carnival.spring.SpringUtilsInitBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 */
public class SpringAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public SpringUtilsInitBean springUtilsInitBean() {
        return SpringUtilsInitBean.INSTANCE;
    }

    @Bean(name = "springId")
    public SpringIdFactoryBean springIdFactoryBean() {
        return new SpringIdFactoryBean();
    }

}
