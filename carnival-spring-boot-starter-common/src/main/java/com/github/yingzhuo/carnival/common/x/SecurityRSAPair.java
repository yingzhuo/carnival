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

import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author 应卓
 * @since 1.6.1
 */
public final class SecurityRSAPair extends Support implements KeyPair<RSAPublicKey, RSAPrivateKey> {

    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;

    public static SecurityRSAPair createRandom(int keySize) {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(RSA);
            generator.initialize(keySize);
            java.security.KeyPair keyPair = generator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            return fromKeys(publicKey, privateKey);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static SecurityRSAPair from(StringRSAKeyPair pair) {
        return fromString(pair.getPublicKey(), pair.getPrivateKey());
    }

    public static SecurityRSAPair fromString(String publicKey, String privateKey) {
        try {
            // 转换公钥
            byte[] publicKeyBytes = decryptBase64(publicKey);
            X509EncodedKeySpec keySpec1 = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory factory1 = KeyFactory.getInstance(RSA);
            RSAPublicKey key1 = (RSAPublicKey) factory1.generatePublic(keySpec1);

            // 转换私钥
            byte[] privateKeyBytes = decryptBase64(privateKey);
            PKCS8EncodedKeySpec keySpec2 = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory factory2 = KeyFactory.getInstance(RSA);
            RSAPrivateKey key2 = (RSAPrivateKey) factory2.generatePrivate(keySpec2);

            return fromKeys(key1, key2);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static SecurityRSAPair fromKeys(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        return new SecurityRSAPair(publicKey, privateKey);
    }

    private SecurityRSAPair(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    @Override
    public RSAPublicKey getPublicKey() {
        return this.publicKey;
    }

    @Override
    public RSAPrivateKey getPrivateKey() {
        return this.privateKey;
    }

}
