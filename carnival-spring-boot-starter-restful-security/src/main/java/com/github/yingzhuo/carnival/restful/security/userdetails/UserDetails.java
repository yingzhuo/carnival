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

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 应卓
 */
public interface UserDetails extends Serializable {

    public static UserDetailsBuilder builder() {
        return new UserDetailsBuilder();
    }

    public Object getId();

    public String getUsername();

    public String getPassword();

    public boolean isExpired();

    public boolean isLocked();

    public Collection<Role> getRoles();

    public Collection<Permission> getPermissions();

    public boolean isAdmin();

    public <U> U getNativeUser();

    public Map<String, Object> getPayload();

    public default Collection<String> getRoleNames() {
        if (getRoles() == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(
                getRoles().stream().map(Role::getName).collect(Collectors.toList()));
    }

    public default Collection<String> getPermissionNames() {
        if (getPermissions() == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(getPermissions().stream().map(Permission::getName).collect(Collectors.toList()));
    }

}
