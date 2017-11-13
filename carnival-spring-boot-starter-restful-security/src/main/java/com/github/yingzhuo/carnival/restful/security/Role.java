/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security;

import com.github.yingzhuo.carnival.restful.security.impl.SimpleRole;

/**
 * 角色
 *
 * @author 应卓
 * @see UserDetails
 * @since 0.0.1
 */
public interface Role {

    public static Role of(String roleName) {
        return new SimpleRole(roleName);
    }

    public String getRoleName();

}
