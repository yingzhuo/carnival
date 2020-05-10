/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.flow.core;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;

/**
 * @author 应卓
 * @since 1.6.6
 */
public class DefaultRequestFlowBean implements RequestFlowBean {

    private final Algorithm algorithm;
    private final Duration tokenTtl;

    public DefaultRequestFlowBean(Algorithm algorithm, Duration tokenTtl) {
        this.algorithm = Objects.requireNonNull(algorithm);
        this.tokenTtl = tokenTtl != null ? tokenTtl : Duration.ofMinutes(5);
    }

    @Override
    public String newToken(String name, int step) {
        long now = System.currentTimeMillis();
        return JWT.create()
                .withClaim("name", name)
                .withClaim("step", step)
                .withClaim("timestamp", now)
                .withExpiresAt(new Date(now + tokenTtl.toMillis()))
                .sign(algorithm);
    }

}
