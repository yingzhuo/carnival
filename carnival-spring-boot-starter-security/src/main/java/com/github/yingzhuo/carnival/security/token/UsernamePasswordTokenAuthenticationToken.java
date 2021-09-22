/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.token;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;

/**
 * @author 应卓
 * @since 1.10.22
 */
public class UsernamePasswordTokenAuthenticationToken extends TokenAuthenticationToken {

    private final String username;
    private final String password;

    public UsernamePasswordTokenAuthenticationToken(String username, String password) {
        this(username, password, null);
    }

    public UsernamePasswordTokenAuthenticationToken(String username, String password, UserDetails userDetails) {
        super(String.format("%s:%s", Objects.requireNonNull(username), Objects.requireNonNull(password)), userDetails);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
