/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.tool.tokendao;

import java.util.Objects;

/**
 * @author 应卓
 * @since 1.2.5
 */
public interface StringTokenDao {

    public void save(String key, String token);

    public String find(String key);

    public default boolean matches(String key, String token) {
        return Objects.equals(find(key), token);
    }

}
