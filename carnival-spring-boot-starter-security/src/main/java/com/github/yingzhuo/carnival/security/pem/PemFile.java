/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.pem;

import lombok.SneakyThrows;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.FileReader;
import java.io.Serializable;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author 应卓
 * @since 1.10.18
 */
public final class PemFile implements Serializable {

    public static final String RSA = "RSA";
    public static final String EC = "EC";

    private final ResourceLoader resourceLoader;
    private final String location;
    private final Resource resource;

    public PemFile(String location) {
        this(new DefaultResourceLoader(), location);
    }

    public PemFile(ResourceLoader resourceLoader, String location) {
        this.resourceLoader = resourceLoader;
        this.location = location;
        this.resource = this.resourceLoader.getResource(location);
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public String getLocation() {
        return location;
    }

    @SneakyThrows
    public PublicKey toPublicKey(String alg) {
        KeyFactory factory = KeyFactory.getInstance(alg);

        try (FileReader keyReader = new FileReader(resource.getFile());
             PemReader pemReader = new PemReader(keyReader)) {

            PemObject pemObject = pemReader.readPemObject();
            byte[] content = pemObject.getContent();
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(content);
            return factory.generatePublic(publicKeySpec);
        }
    }

    @SneakyThrows
    public PrivateKey toPrivateKey(String alg) {
        KeyFactory factory = KeyFactory.getInstance(alg);

        try (FileReader keyReader = new FileReader(resource.getFile());
             PemReader pemReader = new PemReader(keyReader)) {

            PemObject pemObject = pemReader.readPemObject();
            byte[] content = pemObject.getContent();
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(content);
            return factory.generatePrivate(privateKeySpec);
        }
    }

}
