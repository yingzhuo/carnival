/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.realm;

import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.token.UsernamePasswordToken;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;

import java.util.Optional;

/**
 * @author 应卓
 */
public class UsernamePasswordUserDetailsRealm implements UserDetailsRealm {

    private final boolean caseSensitive;
    private final String username;
    private final String password;

    public UsernamePasswordUserDetailsRealm(boolean caseSensitive, String username, String password) {
        this.caseSensitive = caseSensitive;
        this.username = username;
        this.password = password;
    }

    @Override
    public Optional<UserDetails> loadUserDetails(Token token) {
        if (token instanceof UsernamePasswordToken) {
            String uname = ((UsernamePasswordToken) token).getUsername();
            String upass = ((UsernamePasswordToken) token).getPassword();

            UserDetails.Builder builder = UserDetails.builder();

            if (caseSensitive) {
                if (username.equals(uname) && password.equals(upass)) {
                    return Optional.of(builder.username(uname).build());
                }
            } else {
                if (username.equalsIgnoreCase(uname) && password.equalsIgnoreCase(upass)) {
                    return Optional.of(builder.username(uname).build());
                }
            }
        }

        return Optional.empty();
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
