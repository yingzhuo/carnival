/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

/**
 * @author 应卓
 * @since 1.10.2
 */
@SuppressWarnings("unchecked")
class UserDetailsPlusImpl implements UserDetailsPlus {

    private final UserDetails delegate;
    private final Object id;
    private final String nickname;
    private final Object gender;
    private final Object avatar;
    private final Object nativeUser;
    private final String email;
    private final String phoneNumber;
    private final Date dateOfBirth;
    private final String bioInfo;
    private final ExtendedData externalData;

    UserDetailsPlusImpl(UserDetails delegate,
                        Object id,
                        String nickname,
                        Object gender,
                        Object avatar,
                        Object nativeUser,
                        String email,
                        String phoneNumber,
                        Date dateOfBirth,
                        String bioInfo,
                        ExtendedData externalData) {
        this.delegate = Objects.requireNonNull(delegate);
        this.id = id;
        this.nickname = nickname;
        this.gender = gender;
        this.avatar = avatar;
        this.nativeUser = nativeUser;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.bioInfo = bioInfo;
        this.externalData = externalData != null ? externalData : ExtendedData.newInstance();
    }

    @Override
    public <T> T getId() {
        return (T) id;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public <T> T getGender() {
        return (T) gender;
    }

    @Override
    public <T> T getAvatar() {
        return (T) this.avatar;
    }

    @Override
    public <T> T getNativeUser() {
        return (T) nativeUser;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public String getBiography() {
        return bioInfo;
    }

    @Override
    public ExtendedData getExtendedData() {
        return externalData;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return delegate.getAuthorities();
    }

    @Override
    public String getPassword() {
        return delegate.getPassword();
    }

    @Override
    public String getUsername() {
        return delegate.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return delegate.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return delegate.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return delegate.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return delegate.isEnabled();
    }

}
