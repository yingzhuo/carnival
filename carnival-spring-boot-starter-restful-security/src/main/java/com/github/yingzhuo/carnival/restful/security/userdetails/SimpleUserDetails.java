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
import lombok.ToString;

import java.io.Serializable;
import java.util.*;

/**
 * UserDetails简单实现
 *
 * @author 应卓
 * @see UserDetails
 */
@ToString
public class SimpleUserDetails implements UserDetails, Serializable {

    private static final long serialVersionUID = 7811607048302332880L;

    private final Map<String, Object> payload = new HashMap<>(0);
    private Object id = null;
    private String username = null;
    private String password = null;
    private Gender gender = null;
    private Date dob = null;
    private boolean expired = false;
    private boolean locked = false;
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
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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
    @SuppressWarnings("unchecked")
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
