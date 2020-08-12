/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.password.algorithm;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * (内部工具)
 *
 * @author 应卓
 * @since 1.6.27
 */
public final class PasswordEncoderAlgorithms {

    public static final Map<String, PasswordEncoder> SUPPORTED_ALGORITHMS;

    static {
        final Map<String, PasswordEncoder> map = new HashMap<>();
        for (PasswordEncoderAlgorithm algorithm : PasswordEncoderAlgorithm.values()) {
            map.put(algorithm.getId(), algorithm.getPasswordEncoder());
        }
        SUPPORTED_ALGORITHMS = Collections.unmodifiableMap(map);
    }

    private PasswordEncoderAlgorithms() {
    }

}
