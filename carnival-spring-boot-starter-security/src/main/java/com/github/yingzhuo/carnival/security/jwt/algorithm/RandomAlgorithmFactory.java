/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.jwt.algorithm;

import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

/**
 * @author 应卓
 * @since 1.10.19
 */
final class RandomAlgorithmFactory implements AlgorithmFactory {

    @Override
    public Algorithm getObject() {

        int type = RandomUtils.nextInt(0, 3);
        String secret = RandomStringUtils.randomAlphanumeric(36, 72);

        switch (type) {
            case 0:
                return Algorithm.HMAC256(secret);
            case 1:
                return Algorithm.HMAC384(secret);
            case 2:
                return Algorithm.HMAC512(secret);
            default:
                return null;
        }
    }

}
