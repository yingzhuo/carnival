/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.openfeign.target;

import org.springframework.core.env.Environment;

/**
 * @author 应卓
 * @since 1.6.16
 */
@FunctionalInterface
public interface UrlSupplier {

    public static UrlSupplier of(String url) {
        return new FixedUrlSupplier(url);
    }

    public String get(Class<?> clientType, Environment environment);

}
