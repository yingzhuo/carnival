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
public final class NopAlgorithm implements Algorithm {

    @Override
    public String encrypt(String string) {
        return string;
    }

    @Override
    public String decrypt(String string) {
        return string;
    }

}
