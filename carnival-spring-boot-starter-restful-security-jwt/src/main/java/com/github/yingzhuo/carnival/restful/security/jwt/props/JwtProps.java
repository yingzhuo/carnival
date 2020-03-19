/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.jwt.props;

import com.auth0.jwt.algorithms.Algorithm;
import com.github.yingzhuo.carnival.common.io.ResourceText;
import com.github.yingzhuo.carnival.restful.security.jwt.SignatureAlgorithm;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;

/**
 * @author 应卓
 */
@ConfigurationProperties(prefix = "carnival.jwt")
public class JwtProps implements InitializingBean {

    private String secret = JwtProps.class.getName();
    private ResourceText secretLocation = null;
    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HMAC512;
    private Algorithm algorithm;

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(signatureAlgorithm, (String) null);

        if (secretLocation != null) {
            this.secret = this.secretLocation.getTextAsOneLine().trim();
        }

        this.algorithm = signatureAlgorithm.toJwtAlgorithm(this.secret);
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public ResourceText getSecretLocation() {
        return secretLocation;
    }

    public void setSecretLocation(ResourceText secretLocation) {
        this.secretLocation = secretLocation;
    }

    public SignatureAlgorithm getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public void setSignatureAlgorithm(SignatureAlgorithm signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }
}
