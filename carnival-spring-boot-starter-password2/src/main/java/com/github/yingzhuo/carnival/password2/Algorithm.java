/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.password2;

import com.github.yingzhuo.carnival.password2.password.ReversePasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

/**
 * @author 应卓
 */
@SuppressWarnings("deprecation")
public enum Algorithm {

    SHA_1("SHA-1", new MessageDigestPasswordEncoder("SHA-1")),

    SHA_256("SHA-256", new MessageDigestPasswordEncoder("SHA-256")),

    SCRYPT("scrypt", new SCryptPasswordEncoder()),

    PBKDF2("pbkdf2", new Pbkdf2PasswordEncoder()),

    NOOP("noop", NoOpPasswordEncoder.getInstance()),

    MD5("MD5", new MessageDigestPasswordEncoder("MD5")),

    MD4("MD4", new Md4PasswordEncoder()),

    LDAP("ldap", new LdapShaPasswordEncoder()),

    BCRYPT("bcrypt", new BCryptPasswordEncoder()),

    REVERSE("reverse", ReversePasswordEncoder.getInstance());

    // ---------------------------------------------------------------------------------------------------------------

    private String id;

    private PasswordEncoder passwordEncoder;

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
