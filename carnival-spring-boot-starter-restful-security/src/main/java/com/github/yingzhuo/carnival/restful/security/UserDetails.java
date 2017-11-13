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

import com.github.yingzhuo.carnival.restful.security.impl.SimpleUserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public interface UserDetails {

    public String getId();

    public String getUsername();

    public String getPassword();

    public boolean isExpired();

    public boolean isLocked();

    public Collection<Role> getRoles();

    public Collection<Permission> getPermissions();

    public <U> U getNativeUser(Class<U> userType);

    public default Collection<String> getRoleNames() {
        if (getRoles() == null) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(
                getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()));
    }

    public default Collection<String> getPermissionNames() {
        if (getPermissions() == null) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(getPermissions().stream().map(Permission::getPermissionName).collect(Collectors.toList()));
    }

    // Builder
    // --------------------------------------------------------------------------

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private SimpleUserDetails ud = new SimpleUserDetails();

        public Builder id(String id) {
            ud.setId(id);
            return this;
        }

        public Builder id(Supplier<String> idSupplier) {
            ud.setId(idSupplier.get());
            return this;
        }

        public Builder username(String username) {
            ud.setUsername(username);
            return this;
        }

        public Builder username(Supplier<String> usernameSupplier) {
            ud.setUsername(usernameSupplier.get());
            return this;
        }

        public Builder expired(boolean expired) {
            ud.setExpired(expired);
            return this;
        }

        public Builder expired(Supplier<Boolean> expiredSupplier) {
            ud.setExpired(expiredSupplier.get());
            return this;
        }

        public Builder locked(boolean locked) {
            ud.setLocked(locked);
            return this;
        }

        public Builder locked(Supplier<Boolean> lockedSupplier) {
            ud.setLocked(lockedSupplier.get());
            return this;
        }

        public Builder roles(Role... roles) {
            ud.setRoles(Arrays.asList(roles));
            return this;
        }

        public Builder roles(String... roleNames) {
            ud.setRoles(Arrays.stream(roleNames).map(Role::of).collect(Collectors.toList()));
            return this;
        }

        public Builder permissions(Permission... permissions) {
            ud.setPermissions(Arrays.asList(permissions));
            return this;
        }

        public Builder permissions(String... permissionNames) {
            ud.setPermissions(Arrays.stream(permissionNames).map(Permission::of).collect(Collectors.toList()));
            return this;
        }

        public Builder nativeUser(Object nativeUser) {
            ud.setNativeUser(nativeUser);
            return this;
        }

        public UserDetails build() {
            return new SimpleUserDetails();
        }
    }

}
