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

import com.github.yingzhuo.carnival.secret.AESUtils;
import org.springframework.util.Assert;

/**
 * @author 应卓
 * @since 1.6.21
 */
public final class AESAlgorithm implements Algorithm {

    private final String passphrase;

    public AESAlgorithm(String passphrase) {
        Assert.hasText(passphrase, "passphrase is null or empty");
        this.passphrase = passphrase;
    }

    @Override
    public String encrypt(String string) {
        return AESUtils.encrypt(string, passphrase);
    }

    @Override
    public String decrypt(String string) {
        return AESUtils.decrypt(string, passphrase);
    }

}
