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

import com.github.yingzhuo.carnival.common.datamodel.Gender;
import com.github.yingzhuo.carnival.restful.security.role.Permission;
import com.github.yingzhuo.carnival.restful.security.role.Role;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 应卓
 * @since 1.1.13
 */
public final class UserDetailsBuilder {

    private final SimpleUserDetails ud = new SimpleUserDetails();

    public UserDetailsBuilder() {
    }

    public UserDetailsBuilder id(Object id) {
        ud.setId(id);
        return this;
    }

    public UserDetailsBuilder username(String username) {
        ud.setUsername(username);
        return this;
    }

    public UserDetailsBuilder email(String emailAddress) {
        ud.setEmail(emailAddress);
        return this;
    }

    public UserDetailsBuilder password(String password) {
        ud.setPassword(password);
        return this;
    }

    public UserDetailsBuilder gender(Gender gender) {
        ud.setGender(gender);
        return this;
    }

    public UserDetailsBuilder dateOfBirth(Date date) {
        ud.setDateOfBirth(date);
        return this;
    }

    public UserDetailsBuilder expired(boolean expired) {
        ud.setExpired(expired);
        return this;
    }

    public UserDetailsBuilder locked(boolean locked) {
        ud.setLocked(locked);
        return this;
    }

    public UserDetailsBuilder admin(boolean admin) {
        ud.setAdmin(admin);
        return this;
    }

    public UserDetailsBuilder roles(Role... roles) {
        ud.setRoles(Arrays.asList(roles));
        return this;
    }

    public UserDetailsBuilder roles(String... roleNames) {
        ud.setRoles(Arrays.stream(roleNames).map(Role::of).collect(Collectors.toList()));
        return this;
    }

    public UserDetailsBuilder permissions(Permission... permissions) {
        ud.setPermissions(Arrays.asList(permissions));
        return this;
    }

    public UserDetailsBuilder permissions(String... permissionNames) {
        ud.setPermissions(Arrays.stream(permissionNames).map(Permission::of).collect(Collectors.toList()));
        return this;
    }

    public <T> UserDetailsBuilder nativeUser(T nativeUser) {
        ud.setNativeUser(nativeUser);
        return this;
    }

    public UserDetailsBuilder payload(String key, Object value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);

        if (ud.getPayload() == null) {
            ud.setPayload(new HashMap<>());
        }

        ud.getPayload().put(key, value);
        return this;
    }

    public UserDetails build() {
        return ud;
    }

}
