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
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;

import static com.github.yingzhuo.carnival.password.impl.Algorithms.SUPPORTED_ALGORITHMS;

/**
 * @author 应卓
 * @since 1.6.27
 */
public class SmartPasswordEncoder extends DelegatingPasswordEncoder implements PasswordEncoder {

    public SmartPasswordEncoder(Algorithm encoding) {
        this(encoding, null);
    }

    public SmartPasswordEncoder(Algorithm encoding, Algorithm unmapped) {
        super(encoding.getId(), SUPPORTED_ALGORITHMS);

        if (unmapped != null) {
            super.setDefaultPasswordEncoderForMatches(SUPPORTED_ALGORITHMS.get(unmapped.getId()));
        }
    }

    @Override
    public boolean upgradeEncoding(String prefixEncodedPassword) {
        return false;
    }

}
