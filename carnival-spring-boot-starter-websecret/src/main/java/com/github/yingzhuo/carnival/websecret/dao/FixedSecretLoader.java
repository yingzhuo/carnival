/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.websecret.dao;

import java.util.Objects;
import java.util.Optional;

/**
 * @author 应卓
 */
public class FixedSecretLoader implements SecretLoader {

    private final String secret;

    public FixedSecretLoader(String secret) {
        this.secret = Objects.requireNonNull(secret);
    }

    @Override
    public Optional<String> load(String clientId) {
        return Optional.of(secret);
    }

    public String getSecret() {
        return secret;
    }

}
