/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.openfeign;

import org.springframework.core.Ordered;

import static feign.Feign.Builder;

/**
 * @author 应卓
 * @since 1.6.18
 */
@FunctionalInterface
public interface FeignBuilderCustomizer extends Ordered {

    public void customize(Class<?> clientType, Builder builder);

    @Override
    default int getOrder() {
        return 0;
    }

}
