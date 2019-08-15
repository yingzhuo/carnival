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

import java.util.*;

/**
 * UserDetails简单实现
 *
 * @author 应卓
 * @see UserDetails
 */
@SuppressWarnings("unchecked")
class SimpleUserDetails implements UserDetails {

    private final Map<String, Object> payload = new HashMap<>();
    private Object id = null;
    private String username = null;
    private String password = null;
    private Date dob = null;
    private boolean expired = false;
    private boolean locked = false;
    private boolean root = false;
    private Collection<Role> roles = Collections.emptyList();
    private Collection<Permission> permissions = Collections.emptyList();
    private Object nativeUser = null;

    @Override
    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    @Override
    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Collection<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public Date getDateOfBirth() {
        return dob;
    }

    public void setDateOfBirth(Date dob) {
        this.dob = dob;
    }

    @Override
    public <U> U getNativeUser() {
        return (U) this.nativeUser;
    }

    public void setNativeUser(Object nativeUser) {
        this.nativeUser = nativeUser;
    }

    @Override
    public Map<String, Object> getPayload() {
        return Collections.unmodifiableMap(this.payload);
    }

    public void putPayload(String name, Object value) {
        this.payload.put(name, value);
    }

}
