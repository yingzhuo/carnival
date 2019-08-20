/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.permission;

import com.github.yingzhuo.carnival.common.Named;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;

import java.io.Serializable;

/**
 * 权限
 *
 * @author 应卓
 * @see UserDetails
 */
@FunctionalInterface
public interface Permission extends Named, Serializable {

    public static Permission of(String name) {
        return new SimplePermission(name);
    }

}
