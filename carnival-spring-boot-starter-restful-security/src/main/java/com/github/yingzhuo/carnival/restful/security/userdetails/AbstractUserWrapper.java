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

import java.util.Collection;
import java.util.Map;

/**
 * @author 应卓
 * @since 1.6.5
 */
public abstract class AbstractUserWrapper<T> implements UserDetails {

    protected final T user;

    public AbstractUserWrapper(T user) {
        this.user = user;
    }

    @Override
    public Object getId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getUsername() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getPassword() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isExpired() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isLocked() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Role> getRoles() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Permission> getPermissions() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAdmin() {
        throw new UnsupportedOperationException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <U> U getNativeUser() {
        return (U) user;
    }

    @Override
    public Map<String, Object> getPayload() {
        throw new UnsupportedOperationException();
    }

}
