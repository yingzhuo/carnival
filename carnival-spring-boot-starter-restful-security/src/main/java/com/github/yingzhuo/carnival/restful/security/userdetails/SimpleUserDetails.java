/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.userdetails;

import com.github.yingzhuo.carnival.restful.security.role.Permission;
import com.github.yingzhuo.carnival.restful.security.role.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

/**
 * @author 应卓
 */
@Getter
@Setter
@ToString(exclude = {"password"})
class SimpleUserDetails implements UserDetails {

    private Object id = null;
    private String username = null;
    private String password = null;
    private boolean expired = false;
    private boolean locked = false;
    private Collection<Role> roles = Collections.emptyList();
    private Collection<Permission> permissions = Collections.emptyList();
    private boolean admin = false;
    private Object nativeUser = null;
    private Map<String, Object> payload = new HashMap<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleUserDetails that = (SimpleUserDetails) o;

        if (expired != that.expired) return false;
        if (locked != that.locked) return false;
        if (admin != that.admin) return false;
        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(username, that.username)) return false;
        if (!Objects.equals(password, that.password)) return false;
        if (!Objects.equals(roles, that.roles)) return false;
        if (!Objects.equals(permissions, that.permissions)) return false;
        if (!Objects.equals(nativeUser, that.nativeUser)) return false;
        return Objects.equals(payload, that.payload);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (expired ? 1 : 0);
        result = 31 * result + (locked ? 1 : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (permissions != null ? permissions.hashCode() : 0);
        result = 31 * result + (admin ? 1 : 0);
        result = 31 * result + (nativeUser != null ? nativeUser.hashCode() : 0);
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        return result;
    }
}
