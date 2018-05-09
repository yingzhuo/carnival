/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.github.yingzhuo.carnival.jwt.JwtInfo;
import com.github.yingzhuo.carnival.jwt.JwtInfoTransform;
import com.github.yingzhuo.carnival.jwt.JwtTokenGenerator;
import com.github.yingzhuo.carnival.jwt.SignatureAlgorithm;
import com.github.yingzhuo.carnival.jwt.util.AlUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

/**
 * @author 应卓
 */
public class SimpleJwtTokenGenerator implements JwtTokenGenerator {

    private final SignatureAlgorithm signatureAlgorithm;
    private final String secret;
    private final JwtInfoTransform jwtInfoTransform;

    public SimpleJwtTokenGenerator(SignatureAlgorithm signatureAlgorithm, String secret, JwtInfoTransform jwtInfoTransform) {
        this.signatureAlgorithm = signatureAlgorithm;
        this.secret = secret;
        this.jwtInfoTransform = jwtInfoTransform;
    }

    @Override
    public String generate(Object entity) {
        Algorithm al = AlUtils.of(signatureAlgorithm, secret);

        JwtInfo info = jwtInfoTransform.apply(entity);
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

        return builder.sign(al);
    }

    public String getSecret() {
        return secret;
    }

    public JwtInfoTransform getJwtInfoTransform() {
        return jwtInfoTransform;
    }

}
