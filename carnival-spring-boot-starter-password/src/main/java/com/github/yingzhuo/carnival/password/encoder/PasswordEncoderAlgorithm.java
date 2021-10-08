/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.password.encoder;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

/**
 * @author 应卓
 * @see org.springframework.security.crypto.factory.PasswordEncoderFactories
 * @since 1.6.27
 */
@SuppressWarnings("deprecation")
public enum PasswordEncoderAlgorithm {

    bcrypt("bcrypt", new BCryptPasswordEncoder()),
    md5("MD5", new MessageDigestPasswordEncoder("MD5")),
    md4("MD4", new Md4PasswordEncoder()),
    pbkdf2("pbkdf2", new Pbkdf2PasswordEncoder()),
    scrypt("scrypt", new SCryptPasswordEncoder()),
    sha1("SHA-1", new MessageDigestPasswordEncoder("SHA-1")),
    sha256("SHA-256", new MessageDigestPasswordEncoder("SHA-256")),
    argon2("argon2", new Argon2PasswordEncoder()),
    ldap("ldap", new LdapShaPasswordEncoder()),
    sm3("SM3", new SM3PasswordEncoder()),
    base32("base32", new Base32PasswordEncoder()),
    base62("base62", new Base62PasswordEncoder()),
    base64("base64", new Base64PasswordEncoder()),
    reverse("reverse", new ReversePasswordEncoder()),
    noop("noop", NoOpPasswordEncoder.getInstance());

    private final String id;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    PasswordEncoderAlgorithm(String id, org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.id = id;
        this.passwordEncoder = passwordEncoder;
    }

    public String getId() {
        return this.id;
    }

    public PasswordEncoder getPasswordEncoder() {
        return this.passwordEncoder;
    }

}