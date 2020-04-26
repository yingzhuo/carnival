/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.flow;

import com.auth0.jwt.JWT;
import com.github.yingzhuo.carnival.restful.flow.props.FlowProps;
import com.github.yingzhuo.carnival.spring.SpringUtils;
import lombok.val;

import java.util.Date;

/**
 * @author 应卓
 * @since 1.3.6
 */
public final class RequestFlows {

    public static String newToken(String name, int step) {
        val props = SpringUtils.getBean(FlowProps.class);
        val now = System.currentTimeMillis();
        return JWT.create()
                .withClaim("name", name)
                .withClaim("step", step)
                .withClaim("timestamp", now)
                .withExpiresAt(new Date(now + props.getTtl().toMillis()))
                .sign(props.calcAlgorithm());
    }

    private RequestFlows() {
    }

}
