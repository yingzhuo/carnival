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
import com.github.yingzhuo.carnival.nonce.NonceTokenDao;

/**
 * @author 应卓
 * @since 1.6.29
 */
public class NoopNonceTokenDao implements NonceTokenDao {

    @Override
    public void save(NonceToken nonceToken) {
        // nop
    }

    @Override
    public void delete(NonceToken nonceToken) {
        // nop
    }

    @Override
    public boolean exists(NonceToken nonceToken) {
        return false;
    }

}
