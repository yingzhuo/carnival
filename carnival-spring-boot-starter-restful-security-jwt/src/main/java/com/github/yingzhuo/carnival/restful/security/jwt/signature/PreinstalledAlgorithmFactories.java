/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.jwt.signature;

import com.github.yingzhuo.carnival.common.io.ResourceText;

/**
 * @author 应卓
 * @since 1.6.0
 */
public final class PreinstalledAlgorithmFactories {

    public static AlgorithmFactory rsa256(int number) {
        if (number < 0 || number > 9) {
            throw new IllegalArgumentException("number range: [0,10)");
        }
        return new RSA256AlgorithmFactory(
                ResourceText.of(String.format("classpath:/META-INF/rsa/pair%d/public", number)),
                ResourceText.of(String.format("classpath:/META-INF/rsa/pair%d/private", number))
        );
    }

    public static AlgorithmFactory rsa384(int number) {
        if (number < 0 || number > 9) {
            throw new IllegalArgumentException("number range: [0,10)");
        }
        return new RSA384AlgorithmFactory(
                ResourceText.of(String.format("classpath:/META-INF/rsa/pair%d/public", number)),
                ResourceText.of(String.format("classpath:/META-INF/rsa/pair%d/private", number))
        );
    }

    public static AlgorithmFactory rsa512(int number) {
        if (number < 0 || number > 9) {
            throw new IllegalArgumentException("number range: [0,10)");
        }
        return new RSA512AlgorithmFactory(
                ResourceText.of(String.format("classpath:/META-INF/rsa/pair%d/public", number)),
                ResourceText.of(String.format("classpath:/META-INF/rsa/pair%d/private", number))
        );
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static AlgorithmFactory ecdsa256(int number) {
        if (number < 0 || number > 9) {
            throw new IllegalArgumentException("number range: [0,10)");
        }
        return new ECDSA256AlgorithmFactory(
                ResourceText.of(String.format("classpath:/META-INF/ecdsa/pair%d/public", number)),
                ResourceText.of(String.format("classpath:/META-INF/ecdsa/pair%d/private", number))
        );
    }

    public static AlgorithmFactory ecdsa384(int number) {
        if (number < 0 || number > 9) {
            throw new IllegalArgumentException("number range: [0,10)");
        }
        return new ECDSA384AlgorithmFactory(
                ResourceText.of(String.format("classpath:/META-INF/ecdsa/pair%d/public", number)),
                ResourceText.of(String.format("classpath:/META-INF/ecdsa/pair%d/private", number))
        );
    }

    public static AlgorithmFactory ecdsa512(int number) {
        if (number < 0 || number > 9) {
            throw new IllegalArgumentException("number range: [0,10)");
        }
        return new ECDSA512AlgorithmFactory(
                ResourceText.of(String.format("classpath:/META-INF/ecdsa/pair%d/public", number)),
                ResourceText.of(String.format("classpath:/META-INF/ecdsa/pair%d/private", number))
        );
    }

    // -----------------------------------------------------------------------------------------------------------------

    private PreinstalledAlgorithmFactories() {
    }

}
