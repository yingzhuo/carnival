/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.jwt.factory;

import java.util.Objects;

/**
 * @author 应卓
 * @since 1.10.2
 */
@FunctionalInterface
public interface JwtTokenFactory {

    public String create(JwtTokenMetadata metadata);

    public default String create(JwtTokenMetadata.Builder builder) {
        return create(Objects.requireNonNull(builder).build());
    }

}
