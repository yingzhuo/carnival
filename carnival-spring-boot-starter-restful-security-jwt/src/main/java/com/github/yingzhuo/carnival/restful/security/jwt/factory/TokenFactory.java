/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.jwt.factory;

import java.util.function.Function;

/**
 * @author 应卓
 */
@FunctionalInterface
public interface TokenFactory<S, T> extends Function<S, T> {

    public T create(S s);

    public default T apply(S s) {
        return create(s);
    }

}
