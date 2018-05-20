package com.github.yingzhuo.carnival.jwt2.props;

import com.github.yingzhuo.carnival.jwt2.Secret;
import com.github.yingzhuo.carnival.jwt2.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 应卓
 */
@Data
@ConfigurationProperties(prefix = "carnival.jwt.token-factory")
public class TokenFactoryProps {

    private String secret = Secret.DEFAULT;
    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HMAC512;

}
