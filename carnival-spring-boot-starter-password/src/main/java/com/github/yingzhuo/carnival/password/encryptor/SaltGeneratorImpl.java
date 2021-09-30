/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.password.encryptor;

import org.springframework.security.crypto.keygen.KeyGenerators;

/**
 * @author 应卓
 * @since 1.7.1
 */
public class SaltGeneratorImpl implements SaltGenerator {

    @Override
    public String generate() {
        return KeyGenerators.string().generateKey();
    }

}
