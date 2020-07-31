/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.nonce.impl;

import com.github.yingzhuo.carnival.nonce.NonceToken;
import com.github.yingzhuo.carnival.nonce.NonceTokenGenerator;

import java.util.UUID;

/**
 * @author 应卓
 * @since 1.6.32
 */
public class UUIDNonceTokenGenerator implements NonceTokenGenerator {

    @Override
    public NonceToken next() {
        return new StringNonceToken(UUID.randomUUID().toString().replaceAll("-", ""));
    }

}
