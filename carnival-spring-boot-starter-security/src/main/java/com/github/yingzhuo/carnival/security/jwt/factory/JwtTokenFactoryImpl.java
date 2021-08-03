/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.jwt.factory;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.*;

/**
 * @author 应卓
 * @since 1.10.2
 */
public class JwtTokenFactoryImpl implements JwtTokenFactory {

    private final Algorithm alg;
    private final JwtTokenMetadataDefaults defaults;
    private final JwtIdFactory jwtIdFactory;
    private final KeyIdFactory keyIdFactory;

    public JwtTokenFactoryImpl(Algorithm algorithm) {
        this(algorithm, null, null, null);
    }

    public JwtTokenFactoryImpl(Algorithm algorithm, JwtTokenMetadataDefaults defaults) {
        this(algorithm, defaults, null, null);
    }

    public JwtTokenFactoryImpl(Algorithm algorithm, JwtTokenMetadataDefaults defaults, JwtIdFactory jwtIdFactory, KeyIdFactory keyIdFactory) {
        this.alg = Objects.requireNonNull(algorithm);
        this.defaults = defaults;
        this.jwtIdFactory = jwtIdFactory;
        this.keyIdFactory = keyIdFactory;
    }

    @Override
    public String create(JwtTokenMetadata metadata) {
        merge(metadata);

        final JWTCreator.Builder builder = JWT.create();

        Optional.ofNullable(metadata.getKeyId()).ifPresent(builder::withKeyId);
        Optional.ofNullable(metadata.getJwtId()).ifPresent(builder::withJWTId);
        Optional.ofNullable(metadata.getIssuer()).ifPresent(builder::withIssuer);
        Optional.ofNullable(metadata.getSubject()).ifPresent(builder::withSubject);
        Optional.ofNullable(metadata.getExpiresAt()).ifPresent(builder::withExpiresAt);
        Optional.ofNullable(metadata.getNotBefore()).ifPresent(builder::withNotBefore);
        Optional.ofNullable(metadata.getIssuedAt()).ifPresent(builder::withIssuedAt);
        Optional.ofNullable(metadata.getAudience()).ifPresent(it -> {
            if (!it.isEmpty()) {
                builder.withAudience(metadata.getAudience().toArray(new String[0]));
            }
        });

        // Private Claims
        Optional.ofNullable(metadata.getPayloadClaims()).ifPresent(map -> {
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

                if (value instanceof Boolean) {
                    builder.withClaim(name, (Boolean) value);
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

                if (value instanceof Double) {
                    builder.withClaim(name, (Double) value);
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
                }
            }
        });

        if (jwtIdFactory != null && metadata.getJwtId() == null) {
            metadata.setJwtId(jwtIdFactory.create());
            builder.withJWTId(jwtIdFactory.create());
        }

        if (keyIdFactory != null && metadata.getKeyId() == null) {
            metadata.setKeyId(keyIdFactory.create());
            builder.withKeyId(keyIdFactory.create());
        }

        return builder.sign(alg);
    }

    private void merge(JwtTokenMetadata metadata) {
        Objects.requireNonNull(metadata);

        if (this.defaults == null) {
            return;
        }

        JwtTokenMetadata d = defaults.get();

        if (metadata.getJwtId() == null) {
            metadata.setJwtId(d.getJwtId());
        }

        if (metadata.getKeyId() == null) {
            metadata.setKeyId(d.getKeyId());
        }

        if (metadata.getSubject() == null) {
            metadata.setSubject(d.getSubject());
        }

        if (metadata.getIssuer() == null) {
            metadata.setIssuer(d.getIssuer());
        }

        if (metadata.getIssuedAt() == null) {
            metadata.setIssuedAt(d.getIssuedAt());
        }

        if (metadata.getNotBefore() == null) {
            metadata.setNotBefore(d.getNotBefore());
        }

        if (metadata.getExpiresAt() == null) {
            metadata.setExpiresAt(d.getExpiresAt());
        }

        metadata.setAudience(mergeList(d.getAudience(), metadata.getAudience()));
        metadata.setPayloadClaims(mergeMap(d.getPayloadClaims(), metadata.getPayloadClaims()));
    }

    private List<String> mergeList(List<String> list1, List<String> list2) {
        final List<String> list = new ArrayList<>();
        if (list1 != null) list.addAll(list1);
        if (list2 != null) list.addAll(list2);
        return list;
    }

    private Map<String, Object> mergeMap(Map<String, Object> map1, Map<String, Object> map2) {
        final Map<String, Object> map = new HashMap<>();
        if (map1 != null) map.putAll(map1);
        if (map2 != null) map.putAll(map2);
        return map;
    }

}
