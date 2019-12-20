/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.flow.props;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 应卓
 * @since 1.3.6
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "carnival.flow")
public class FlowProps {

    private boolean enabled = true;
    private String secret = FlowProps.class.getName();
    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HMAC512;
    private String[] interceptorPatterns = new String[]{"/", "/**"};

    // ------------------------------------------------------------------------------------------------

    public Algorithm calcAlgorithm() {
        return signatureAlgorithm.toJwtAlgorithm(this.secret);
    }

}
