/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.secret.rsa;

import com.github.yingzhuo.carnival.secret.AbstractSecuritySupport;
import com.github.yingzhuo.carnival.secret.KeyPair;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @author 应卓
 * @since 1.6.1
 */
public final class RSAKeyPair extends AbstractSecuritySupport implements KeyPair<String, String> {

    private final String base64PublicKey;
    private final String base64PrivateKey;

    private RSAKeyPair(String publicKey, String privateKey) {
        this.base64PublicKey = publicKey;
        this.base64PrivateKey = privateKey;
    }

    public static RSAKeyPair createRandom(int keySize) {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(RSA);
            generator.initialize(keySize);
            java.security.KeyPair keyPair = generator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            return new RSAKeyPair(
                    encryptBase64(publicKey.getEncoded()),
                    encryptBase64(privateKey.getEncoded())
            );
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static RSAKeyPair fromString(String publicKey, String privateKey) {
        return new RSAKeyPair(publicKey, privateKey);
    }

    @Override
    public String getBase64PublicKey() {
        return this.base64PublicKey;
    }

    @Override
    public String getBase64PrivateKey() {
        return this.base64PrivateKey;
    }

}
