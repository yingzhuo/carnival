/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.mvc;

/**
 * @author 应卓
 * @since 1.6.8
 */
@FunctionalInterface
public interface MvcInterceptorConfigurer {

    public int getOrder();

    public default String[] getPathPatterns() {
        return new String[]{"/", "/**"};
    }

}
