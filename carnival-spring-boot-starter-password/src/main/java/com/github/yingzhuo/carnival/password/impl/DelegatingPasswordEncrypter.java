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
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author 应卓
 */
public class DelegatingPasswordEncrypter implements PasswordEncrypter {

    private String idPrefix = "{";
    private String idSuffix = "}";
    private final String defaultId;
    private final Map<String, PasswordEncrypter> idToPasswordEncrypter;

    public DelegatingPasswordEncrypter(String defaultId) {
        Assert.hasLength(defaultId, () -> null);
        this.defaultId = defaultId;

        Map<String, PasswordEncrypter> map = new HashMap<>();
        map.put("none", new NonePasswordEncrypter());
        map.put("md5", new MD5PasswordEncrypter());
        map.put("md2", new MD2PasswordEncrypter());
        map.put("sha1", new SHA1PasswordEncrypter());
        map.put("sha256", new SHA256PasswordEncrypter());
        map.put("reverse", new ReversePasswordEncrypter());
        map.put("base64", new Base64PasswordEncrypter());
        map.put("bcrypt", new BCryptPasswordEncrypter());
        this.idToPasswordEncrypter = Collections.unmodifiableMap(map);
    }

    public DelegatingPasswordEncrypter(String defaultId, Map<String, PasswordEncrypter> idToPasswordEncrypter) {
        Assert.hasLength(defaultId, () -> null);
        Assert.notEmpty(idToPasswordEncrypter, () -> null);
        Assert.state(idToPasswordEncrypter.keySet().contains(defaultId), () -> null);

        this.defaultId = defaultId;
        this.idToPasswordEncrypter = Collections.unmodifiableMap(idToPasswordEncrypter);
    }

    @Override
    public String encrypt(String password) {

        Objects.requireNonNull(password);

        PasswordEncrypter delegated;

        for (String it : idToPasswordEncrypter.keySet()) {
            val s = idPrefix + it + idSuffix;
            if (password.startsWith(s)) {
                delegated = idToPasswordEncrypter.get(it);
                return s + delegated.encrypt(password.substring(s.length()));
            }
        }

        delegated = idToPasswordEncrypter.get(defaultId);
        return idPrefix + defaultId + idSuffix + delegated.encrypt(password);
    }

    public void setIdPrefix(String idPrefix) {
        this.idPrefix = idPrefix;
    }

    public void setIdSuffix(String idSuffix) {
        this.idSuffix = idSuffix;
    }

}
