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
import lombok.val;
import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 应卓
 */
public interface UserDetails extends Serializable {

    public static Builder builder() {
        return new Builder();
    }

    public Object getId();

    public String getUsername();

    public Date getDateOfBirth();

    public String getPassword();

    public boolean isExpired();

    public default boolean isNotExpired() {
        return !isExpired();
    }

    public boolean isLocked();

    public default boolean isNotLocked() {
        return !isLocked();
    }

    public boolean isRoot();

    public default boolean isNotRoot() {
        return !isRoot();
    }

    public Collection<Role> getRoles();

    public Collection<Permission> getPermissions();

    public <U> U getNativeUser();

    public Map<String, Object> getPayload();

    public default int getPayloadSize() {
        val payload = getPayload();
        return payload != null ? payload.size() : 0;
    }

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

    // Builder
    // -----------------------------------------------------------------------------------------------------------------

    public static class Builder {

        private SimpleUserDetails ud = new SimpleUserDetails();

        private Builder() {
            super();
        }

        public Builder id(Object id) {
            ud.setId(id);
            return this;
        }

        public Builder username(String username) {
            ud.setUsername(username);
            return this;
        }

        public Builder password(String password) {
            ud.setPassword(password);
            return this;
        }

        public Builder dateOfBirth(Date date) {
            ud.setDateOfBirth(date);
            return this;
        }

        public Builder dateOfBirth(int year, int month, int day) {
            Date date = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
            date = DateUtils.setYears(date, year);
            date = DateUtils.setMonths(date, month);
            date = DateUtils.setDays(date, day);
            return dateOfBirth(date);
        }

        public Builder expired(boolean expired) {
            ud.setExpired(expired);
            return this;
        }

        public Builder locked(boolean locked) {
            ud.setLocked(locked);
            return this;
        }

        public Builder root(boolean root) {
            ud.setRoot(root);
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

        public Builder payload(String name, Object value) {
            Objects.requireNonNull(name);
            Objects.requireNonNull(value);
            ud.putPayload(name, value);
            return this;
        }

        public UserDetails build() {
            return ud;
        }
    }

}
