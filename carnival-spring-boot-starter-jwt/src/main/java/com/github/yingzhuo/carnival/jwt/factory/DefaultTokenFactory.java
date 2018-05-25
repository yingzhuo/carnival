/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt.factory;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.github.yingzhuo.carnival.jwt.SignatureAlgorithm;
import com.github.yingzhuo.carnival.jwt.util.InternalUtls;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

/**
 * @author 应卓
 */
public class DefaultTokenFactory implements TokenFactory {

    private String secret;
    private SignatureAlgorithm signatureAlgorithm;

    public DefaultTokenFactory() {
        super();
    }

    @Override
    public String create(TokenInfo info) {
        JWTCreator.Builder builder = JWT.create();

        // Public Claims (Public)
        Optional.ofNullable(info.getKeyId()).ifPresent(builder::withKeyId);

        // Public Claims (Payload)
        Optional.ofNullable(info.getIssuer()).ifPresent(builder::withIssuer);
        Optional.ofNullable(info.getSubject()).ifPresent(builder::withSubject);
        Optional.ofNullable(info.getExpiresAt()).ifPresent(builder::withExpiresAt);
        Optional.ofNullable(info.getNotBefore()).ifPresent(builder::withNotBefore);
        Optional.ofNullable(info.getIssuedAt()).ifPresent(builder::withIssuedAt);
        Optional.ofNullable(info.getJwtId()).ifPresent(builder::withJWTId);
        Optional.ofNullable(info.getAudience()).ifPresent(it -> {
            if (!it.isEmpty()) {
                builder.withAudience(info.getAudience().toArray(new String[info.getAudience().size()]));
            }
        });

        // Private Claims
        Optional.ofNullable(info.getPrivateClaims()).ifPresent(map -> {
            final Set<String> keySet = map.keySet();
            for (String name : keySet) {
                Object value = map.get(name);

                if (value instanceof String) {
                    builder.withClaim(name, (String) value);
                    continue;
                }

                if (value instanceof Integer) {
                    builder.withClaim(name, (Integer) value);
                    continue;
                }

                if (value instanceof Date) {
                    builder.withClaim(name, (Date) value);
                    continue;
                }

                if (value instanceof Long) {
                    builder.withClaim(name, (Long) value);
                    continue;
                }

                if (value instanceof String[]) {
                    builder.withArrayClaim(name, (String[]) value);
                    continue;
                }

                if (value instanceof Integer[]) {
                    builder.withArrayClaim(name, (Integer[]) value);
                    continue;
                }

                if (value instanceof Long[]) {
                    builder.withArrayClaim(name, (Long[]) value);
                    // continue;
                }
            }
        });

        return builder.sign(InternalUtls.toAlgorithm(signatureAlgorithm, secret));
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setSignatureAlgorithm(SignatureAlgorithm signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }
}
