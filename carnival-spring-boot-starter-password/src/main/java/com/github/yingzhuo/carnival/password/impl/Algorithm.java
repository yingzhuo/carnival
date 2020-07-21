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

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

/**
 * @author 应卓
 * @since 1.6.27
 */
@SuppressWarnings("deprecation")
public enum Algorithm {

    bcrypt("bcrypt", new BCryptPasswordEncoder()),
    md5("MD5", new MessageDigestPasswordEncoder("MD5")),
    md4("MD4", new Md4PasswordEncoder()),
    pbkdf2("pbkdf2", new Pbkdf2PasswordEncoder()),
    scrypt("scrypt", new SCryptPasswordEncoder()),
    sha1("SHA-1", new MessageDigestPasswordEncoder("SHA-1")),
    sha256("SHA-256", new MessageDigestPasswordEncoder("SHA-256")),
    argon2("argon2", new Argon2PasswordEncoder()),
    ldap("ldap", new LdapShaPasswordEncoder()),
    base64("base64", new Base64PasswordEncoder()),
    reverse("reverse", new ReversePasswordEncoder()),
    noop("noop", NoOpPasswordEncoder.getInstance());

    private final String id;
    private final PasswordEncoder passwordEncoder;

    Algorithm(String id, PasswordEncoder passwordEncoder) {
        this.id = id;
        this.passwordEncoder = passwordEncoder;
    }

    public String getId() {
        return id;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

}
