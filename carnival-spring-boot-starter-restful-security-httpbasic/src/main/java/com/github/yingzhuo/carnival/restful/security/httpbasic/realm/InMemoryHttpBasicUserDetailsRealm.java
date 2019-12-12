/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.httpbasic.realm;

import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Objects;
import java.util.Properties;

/**
 * @author 应卓
 * @since 1.3.5
 */
public class InMemoryHttpBasicUserDetailsRealm extends AbstractHttpBasicUserDetailsRealm {

    private Properties properties;

    public InMemoryHttpBasicUserDetailsRealm() {
        this(new Properties());
    }

    public InMemoryHttpBasicUserDetailsRealm(Properties properties) {
        this.properties = properties;
    }

    public InMemoryHttpBasicUserDetailsRealm(String username, String password) {
        this(new Properties());
        properties.put(username, password);
    }

    @Override
    protected UserDetails doLoadUserDetails(String username, String password) {

        if (Objects.equals(properties.getProperty(username), password)) {
            return UserDetails.builder()
                    .username(username)
                    .password(password)
                    .build();
        }

        return null;
    }

    public void load(InputStream inputStream) {
        try {
            this.properties.load(inputStream);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void loadFromXML(InputStream inputStream) {
        try {
            this.properties.loadFromXML(inputStream);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
