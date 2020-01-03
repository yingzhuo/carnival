/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.persistence;

import com.github.yingzhuo.carnival.restful.security.token.Token;

/**
 * @author 应卓
 * @since 1.4.0
 */
public interface TokenPersistenceUnit<T extends Token, U> {

    public void save(T token, U user);

    public U findByToken(T token);

    public default boolean exists(T token) {
        return findByToken(token) != null;
    }

}
