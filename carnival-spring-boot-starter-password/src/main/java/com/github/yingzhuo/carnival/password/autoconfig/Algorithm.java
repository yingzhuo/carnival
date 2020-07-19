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

/**
 * @author 应卓
 * @since 1.6.27
 */
public enum Algorithm {

    noop("noop"),
    bcrypt("bcrypt"),
    ldap("ldap"),
    md4("MD4"),
    md5("MD5"),
    pbkdf2("pbkdf2"),
    scrypt("scrypt"),
    sha1("SHA-1"),
    sha256("SHA-256"),
    argon2("argon2");

    private final String id;

    Algorithm(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
