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

import com.github.yingzhuo.carnival.restful.security.impl.SimplePermission;

/**
 * 权限
 *
 * @author 应卓
 * @see UserDetails
 * @since 0.0.1
 */
public interface Permission {

    public static Permission of(String permissionName) {
        return new SimplePermission(permissionName);
    }

    public String getPermissionName();

}
