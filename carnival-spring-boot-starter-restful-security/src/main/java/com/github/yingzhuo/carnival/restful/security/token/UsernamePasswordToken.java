/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.token;

import lombok.Getter;

import java.util.Objects;

/**
 * @author 应卓
 */
@Getter
public class UsernamePasswordToken implements Token {

    public static UsernamePasswordToken of(String username, String password) {
        return new UsernamePasswordToken(username, password);
    }

    private final String username;
    private final String password;

    public UsernamePasswordToken(String username, String password) {
        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
    }

    @Override
    public String toString() {
        return "UsernamePasswordToken(" +
                "value='" + username + '\'' +
                ", password='" + password + '\'' +
                ')';
    }

}
