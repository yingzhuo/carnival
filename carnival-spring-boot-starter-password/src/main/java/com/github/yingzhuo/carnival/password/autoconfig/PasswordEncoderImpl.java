/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.password.autoconfig;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 * @since 1.6.27
 */
public class PasswordEncoderImpl extends DelegatingPasswordEncoder implements com.github.yingzhuo.carnival.password.PasswordEncoder {

    private static final Map<String, PasswordEncoder> SUPPORTED = encoders();

    public PasswordEncoderImpl(Algorithm encoding, Algorithm unmapped) {
        super(encoding.getId(), SUPPORTED);

        if (unmapped != null) {
            super.setDefaultPasswordEncoderForMatches(SUPPORTED.get(unmapped.getId()));
        }
    }

    @SuppressWarnings("deprecation")
    private static Map<String, PasswordEncoder> encoders() {
        final Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(Algorithm.bcrypt.getId(), new BCryptPasswordEncoder());
        encoders.put(Algorithm.ldap.getId(), new LdapShaPasswordEncoder());
        encoders.put(Algorithm.md4.getId(), new Md4PasswordEncoder());
        encoders.put(Algorithm.md5.getId(), new MessageDigestPasswordEncoder("MD5"));
        encoders.put(Algorithm.noop.getId(), NoOpPasswordEncoder.getInstance());
        encoders.put(Algorithm.pbkdf2.getId(), new Pbkdf2PasswordEncoder());
        encoders.put(Algorithm.scrypt.getId(), new SCryptPasswordEncoder());
        encoders.put(Algorithm.sha1.getId(), new MessageDigestPasswordEncoder("SHA-1"));
        encoders.put(Algorithm.sha256.getId(), new MessageDigestPasswordEncoder("SHA-256"));
        encoders.put(Algorithm.argon2.getId(), new Argon2PasswordEncoder());
        return Collections.unmodifiableMap(encoders);
    }

}
