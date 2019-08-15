/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.role;

import com.github.yingzhuo.carnival.common.Named;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;

import java.io.Serializable;

/**
 * 角色
 *
 * @author 应卓
 * @see UserDetails
 */
@FunctionalInterface
public interface Role extends Named, Serializable {

    public static Role of(String name) {
        return new SimpleRole(name);
    }

}
