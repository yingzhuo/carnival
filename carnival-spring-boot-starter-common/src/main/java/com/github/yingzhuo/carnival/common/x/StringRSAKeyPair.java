/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.x;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @author 应卓
 * @since 1.6.1
 */
public final class StringRSAKeyPair extends Support implements KeyPair<String, String> {

    private final String publicKey;
    private final String privateKey;

    public static StringRSAKeyPair createRandom(int keySize) {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(RSA);
            generator.initialize(keySize);
            java.security.KeyPair keyPair = generator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            return fromKeys(publicKey, privateKey);
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError();
        }
    }

    public static StringRSAKeyPair from(SecurityRSAPair pair) {
        return fromKeys(pair.getPublicKey(), pair.getPrivateKey());
    }

    public static StringRSAKeyPair fromKeys(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        return new StringRSAKeyPair(
                encryptBase64(publicKey.getEncoded()),
                encryptBase64(privateKey.getEncoded())
        );
    }

    public static StringRSAKeyPair fromString(String publicKey, String privateKey) {
        return new StringRSAKeyPair(publicKey, privateKey);
    }

    private StringRSAKeyPair(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    @Override
    public String getPublicKey() {
        return this.publicKey;
    }

    @Override
    public String getPrivateKey() {
        return this.privateKey;
    }

}
