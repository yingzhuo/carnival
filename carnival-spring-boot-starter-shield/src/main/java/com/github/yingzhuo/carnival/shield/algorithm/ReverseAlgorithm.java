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
 * @since 1.6.22
 */
public final class ReverseAlgorithm implements Algorithm {

    @Override
    public String encrypt(String string) {
        return new StringBuilder(string)
                .reverse()
                .toString();
    }

    @Override
    public String decrypt(String string) {
        return new StringBuilder(string)
                .reverse()
                .toString();
    }

}
