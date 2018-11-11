/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt.props;

import com.github.yingzhuo.carnival.jwt.Secret;
import com.github.yingzhuo.carnival.jwt.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;

@Slf4j
@Getter
@Setter
@ConfigurationProperties(prefix = "carnival.jwt")
public class JwtProps implements InitializingBean {

    private String secret = Secret.DEFAULT;
    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HMAC512;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(secret, (String) null);
        Assert.notNull(signatureAlgorithm, (String) null);
        log.info("carnival.jwt.secret = {}", this.secret);
        log.info("carnival.jwt.signature-algorithm = {}", this.signatureAlgorithm);
    }

}
