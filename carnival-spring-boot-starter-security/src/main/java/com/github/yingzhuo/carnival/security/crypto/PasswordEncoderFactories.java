/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.crypto;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author 应卓
 * @see org.springframework.security.crypto.factory.PasswordEncoderFactories
 * @since 1.10.28
 */
@SuppressWarnings("deprecation")
public final class PasswordEncoderFactories {

    public static final Map<String, PasswordEncoder> SUPPORTED_ENCODERS;

    static {
        final Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("ldap", new LdapShaPasswordEncoder());
        encoders.put("MD4", new Md4PasswordEncoder());
        encoders.put("MD5", new MessageDigestPasswordEncoder("MD5"));
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("SHA-1", new MessageDigestPasswordEncoder("SHA-1"));
        encoders.put("SHA-256", new MessageDigestPasswordEncoder("SHA-256"));
        encoders.put("argon2", new Argon2PasswordEncoder());
        SUPPORTED_ENCODERS = Collections.unmodifiableMap(encoders);
    }

    private PasswordEncoderFactories() {
    }

    public static PasswordEncoder getById(String idForEncode) {
        return Optional.ofNullable(SUPPORTED_ENCODERS.get(idForEncode))
                .orElseThrow(() -> new IllegalArgumentException(idForEncode + " not supported"));
    }

    public static DelegatingPasswordEncoder delegating() {
        return delegating("bcrypt");
    }

    public static DelegatingPasswordEncoder delegating(String idForEncode) {
        return delegating(idForEncode, null);
    }

    public static DelegatingPasswordEncoder delegating(String idForEncode, String defaultIdForMatches) {
        final DelegatingPasswordEncoder encoder = new DelegatingPasswordEncoder(idForEncode, SUPPORTED_ENCODERS);

        if (defaultIdForMatches != null) {
            encoder.setDefaultPasswordEncoderForMatches(getById(defaultIdForMatches));
        }

        return encoder;
    }

}
