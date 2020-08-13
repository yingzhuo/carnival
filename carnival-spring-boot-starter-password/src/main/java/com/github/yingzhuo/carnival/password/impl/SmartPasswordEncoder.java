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

import com.github.yingzhuo.carnival.password.PasswordEncoder;
import com.github.yingzhuo.carnival.password.algorithm.PasswordEncoderAlgorithm;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 * @since 1.6.27
 */
public class SmartPasswordEncoder extends DelegatingPasswordEncoder implements PasswordEncoder {

    private static final Map<String, org.springframework.security.crypto.password.PasswordEncoder> ID_TO_ENCODERS;

    static {
        ID_TO_ENCODERS = new HashMap<>();
        for (PasswordEncoderAlgorithm algorithm : PasswordEncoderAlgorithm.values()) {
            ID_TO_ENCODERS.put(algorithm.getId(), algorithm.getPasswordEncoder());
        }
    }

    public SmartPasswordEncoder() {
        this(PasswordEncoderAlgorithm.md5, PasswordEncoderAlgorithm.md5);
    }

    public SmartPasswordEncoder(PasswordEncoderAlgorithm encoding, PasswordEncoderAlgorithm unmapped) {
        super(encoding.getId(), ID_TO_ENCODERS);

        if (unmapped != null) {
            setDefaultPasswordEncoderForMatches(ID_TO_ENCODERS.get(unmapped.getId()));
        }
    }

    @Override
    public boolean upgradeEncoding(String prefixEncodedPassword) {
        return false;
    }

}
