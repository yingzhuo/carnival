/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.password.impl;

import com.github.yingzhuo.carnival.password.PasswordEncrypter;
import lombok.val;
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
@SuppressWarnings("deprecation")
public class SmartPasswordEncrypter extends PasswordEncoderEncrypter implements PasswordEncrypter {

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

    public SmartPasswordEncrypter(String idForEncode) {
        this(idForEncode, null);
    }

    public SmartPasswordEncrypter(String idForEncode, PasswordEncoder defaultEncoder) {
        super(createPasswordEncoder(idForEncode, defaultEncoder));
    }

    private static PasswordEncoder createPasswordEncoder(String idForEncode, PasswordEncoder defaultPasswordEncoder) {
        if (SUPPORTED_ENCODERS.get(idForEncode) == null) {
            String msg = String.format("id '%s' is NOT supported.", idForEncode);
            throw new IllegalArgumentException(msg);
        }

        val encoder = new DelegatingPasswordEncoder(idForEncode, SUPPORTED_ENCODERS);
        if (defaultPasswordEncoder != null) {
            encoder.setDefaultPasswordEncoderForMatches(defaultPasswordEncoder);
        }
        return encoder;
    }

}
