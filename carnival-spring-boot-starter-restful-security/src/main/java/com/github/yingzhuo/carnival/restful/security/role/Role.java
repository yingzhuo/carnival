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
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

/**
 * 角色
 *
 * @author 应卓
 * @see UserDetails
 */
public interface Role extends Serializable, Named {

    public static Role of(String name) {
        return new SimpleRole(name);
    }

    public String getName();

    // -----------------------------------------------------------------------------------------------------------------

    @ToString
    public static final class SimpleRole implements Role {

        private static final long serialVersionUID = 2563135365649264127L;

        private final String name;

        public SimpleRole(String name) {
            this.name = Objects.requireNonNull(name);
        }

        @Override
        public String getName() {
            return name;
        }
    }

}
