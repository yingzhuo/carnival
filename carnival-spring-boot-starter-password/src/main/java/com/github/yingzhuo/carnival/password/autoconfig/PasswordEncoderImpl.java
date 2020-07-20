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

import org.springframework.security.crypto.password.DelegatingPasswordEncoder;

import static com.github.yingzhuo.carnival.password.autoconfig.Algorithms.SUPPORTED_ALGORITHMS;

/**
 * @author 应卓
 * @since 1.6.27
 */
public class PasswordEncoderImpl extends DelegatingPasswordEncoder implements com.github.yingzhuo.carnival.password.PasswordEncoder {

    public PasswordEncoderImpl(Algorithm encoding, Algorithm unmapped) {
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
