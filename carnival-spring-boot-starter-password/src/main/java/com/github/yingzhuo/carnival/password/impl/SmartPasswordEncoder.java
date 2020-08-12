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
import com.github.yingzhuo.carnival.password.algorithm.PasswordEncoderAlgorithms;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;

/**
 * @author 应卓
 * @since 1.6.27
 */
public class SmartPasswordEncoder extends DelegatingPasswordEncoder implements PasswordEncoder {

    public SmartPasswordEncoder(PasswordEncoderAlgorithm encoding, PasswordEncoderAlgorithm unmapped) {
        super(encoding.getId(), PasswordEncoderAlgorithms.SUPPORTED_ALGORITHMS);

        if (unmapped != null) {
            setDefaultPasswordEncoderForMatches(PasswordEncoderAlgorithms.SUPPORTED_ALGORITHMS.get(unmapped.getId()));
        }
    }

    @Override
    public boolean upgradeEncoding(String prefixEncodedPassword) {
        return false;
    }

}
