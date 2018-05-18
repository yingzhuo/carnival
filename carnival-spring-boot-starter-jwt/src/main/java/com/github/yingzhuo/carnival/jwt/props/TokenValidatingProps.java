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
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 应卓
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "carnival.jwt.token-validating")
public class TokenValidatingProps {

    private boolean enabled = true;
    private String secret = Secret.DEFAULT;
    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HMAC512;
    private Set<String> excludePatterns = new HashSet<>();

}
