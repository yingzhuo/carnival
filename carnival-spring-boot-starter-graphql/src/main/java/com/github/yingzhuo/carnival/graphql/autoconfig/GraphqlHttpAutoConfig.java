/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.graphql.autoconfig;

import com.github.yingzhuo.carnival.graphql.threadlocal.RequestAttributesThreadLocalAccessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author 应卓
 * @since 1.10.16
 */
@ConditionalOnWebApplication
@ConditionalOnMissingBean(RequestAttributesThreadLocalAccessor.class)
class GraphqlHttpAutoConfig {

    @Bean
    RequestAttributesThreadLocalAccessor requestAttributesAccessor() {
        return new RequestAttributesThreadLocalAccessor();
    }

}
