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

import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

/**
 * 权限
 *
 * @author 应卓
 * @see UserDetails
 */
public interface Permission extends Serializable {

    public static Permission of(String name) {
        return new SimplePermission(name);
    }

    public String getName();

    // -----------------------------------------------------------------------------------------------------------------

    @ToString
    public static final class SimplePermission implements Permission {

        private static final long serialVersionUID = -7421422978387481040L;

        private final String name;

        public SimplePermission(String name) {
            this.name = Objects.requireNonNull(name);
        }

        @Override
        public String getName() {
            return name;
        }
    }

}
