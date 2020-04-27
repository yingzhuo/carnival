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

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

import java.io.Serializable;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author 应卓
 * @since 1.3.6
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "carnival.flow")
public class FlowProps implements Serializable {

    private boolean enabled = true;

    private String secret = FlowProps.class.getName();

    @DurationUnit(ChronoUnit.MINUTES)
    private Duration ttl = Duration.ofMinutes(10L); // 默认10分钟过期

    private String[] interceptorPatterns = new String[]{"/", "/**"};

}
