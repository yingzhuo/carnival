/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.jwt.algorithm;

import com.auth0.jwt.algorithms.Algorithm;
import com.github.yingzhuo.carnival.security.pem.PemFile;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @author 应卓
 * @since 1.10.18
 */
public final class PemFileAlgorithmFactory implements AlgorithmFactory, InitializingBean {

    private final Alg alg;
    private final PemFile privatePemFile;
    private final PemFile publicPemFile;

    public PemFileAlgorithmFactory(Alg alg, PemFile publicPemFile, PemFile privatePemFile) {
        this.alg = alg;
        this.privatePemFile = privatePemFile;
        this.publicPemFile = publicPemFile;
    }

    public PemFileAlgorithmFactory(Alg alg, String publicPemFile, String privatePemFile) {
        this(alg, new PemFile(publicPemFile), new PemFile(privatePemFile));
    }

    @Override
    public Algorithm getObject() {
        switch (this.alg) {
            case ECDSA256:
                return Algorithm.ECDSA256(
                        (ECPublicKey) publicPemFile.toPublicKey(PemFile.EC),
                        (ECPrivateKey) privatePemFile.toPrivateKey(PemFile.EC)
                );
            case ECDSA256K:
                return Algorithm.ECDSA256K(
                        (ECPublicKey) publicPemFile.toPublicKey(PemFile.EC),
                        (ECPrivateKey) privatePemFile.toPrivateKey(PemFile.EC)
                );
            case ECDSA384:
                return Algorithm.ECDSA384(
                        (ECPublicKey) publicPemFile.toPublicKey(PemFile.EC),
                        (ECPrivateKey) privatePemFile.toPrivateKey(PemFile.EC)
                );
            case ECDSA512:
                return Algorithm.ECDSA512(
                        (ECPublicKey) publicPemFile.toPublicKey(PemFile.EC),
                        (ECPrivateKey) privatePemFile.toPrivateKey(PemFile.EC)
                );
            case RSA256:
                return Algorithm.RSA256(
                        (RSAPublicKey) publicPemFile.toPublicKey(PemFile.RSA),
                        (RSAPrivateKey) privatePemFile.toPrivateKey(PemFile.RSA)
                );
            case RSA384:
                return Algorithm.RSA384(
                        (RSAPublicKey) publicPemFile.toPublicKey(PemFile.RSA),
                        (RSAPrivateKey) privatePemFile.toPrivateKey(PemFile.RSA)
                );
            case RSA512:
                return Algorithm.RSA512(
                        (RSAPublicKey) publicPemFile.toPublicKey(PemFile.RSA),
                        (RSAPrivateKey) privatePemFile.toPrivateKey(PemFile.RSA)
                );
            default:
                return null;
        }
    }

    @Override
    public final Class<?> getObjectType() {
        return Algorithm.class;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(alg, "alg is null");
        Assert.notNull(privatePemFile, "privatePemFile is null");
        Assert.notNull(publicPemFile, "publicPemFile is null");
    }

    public enum Alg {
        ECDSA256,
        ECDSA256K,
        ECDSA384,
        ECDSA512,
        RSA256,
        RSA384,
        RSA512
    }

}
