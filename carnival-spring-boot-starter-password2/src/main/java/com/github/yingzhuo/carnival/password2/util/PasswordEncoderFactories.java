/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.password2.util;

import com.github.yingzhuo.carnival.password2.Algorithm;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author 应卓
 */
public final class PasswordEncoderFactories {

    public static DelegatingPasswordEncoder createDelegatingPasswordEncoder() {
        return createDelegatingPasswordEncoder(Algorithm.MD5);
    }

    public static DelegatingPasswordEncoder createDelegatingPasswordEncoder(Algorithm defaultAlgorithm) {
        Objects.requireNonNull(defaultAlgorithm);

        final Map<String, PasswordEncoder> encoders = new HashMap<>();
        for (Algorithm alg : Algorithm.values()) {
            encoders.put(alg.getId(), alg.getPasswordEncoder());
        }
        return new DelegatingPasswordEncoder(defaultAlgorithm.getId(), encoders);
    }

    private PasswordEncoderFactories() {
    }

}
