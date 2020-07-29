/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.responsebody.algorithm;

import com.github.yingzhuo.carnival.restful.security.responsebody.ResponseBodyEncryptingAlgorithm;
import com.github.yingzhuo.carnival.secret.AESUtils;

/**
 * @author 应卓
 * @since 1.6.30
 */
public class AESResponseBodyEncryptingAlgorithm implements ResponseBodyEncryptingAlgorithm {

    private final String passphrase;

    public AESResponseBodyEncryptingAlgorithm(String passphrase) {
        this.passphrase = passphrase;
    }

    @Override
    public String encrypt(String body) {
        return AESUtils.encrypt(body, passphrase);
    }

}
