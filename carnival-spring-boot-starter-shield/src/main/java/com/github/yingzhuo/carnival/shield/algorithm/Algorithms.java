/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.shield.algorithm;

/**
 * @author 应卓
 * @since 1.6.21
 */
public final class Algorithms {

    private Algorithms() {
    }

    @Deprecated
    public static Algorithm nop() {
        return new NopAlgorithm();
    }

    public static Algorithm aes(String passphrase) {
        return new AESAlgorithm(passphrase);
    }

}
