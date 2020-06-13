/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.openfeign.props;

import feign.Logger;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author 应卓
 * @since 1.6.16
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "carnival.openfeign")
public class FeignProperties {

    private Logger.Level loggerLevel = Logger.Level.BASIC;
    private String loggerName = "OpenFeign";
    private boolean decode404 = false;
    private BasicAuth basicAuth;
    private BearerAuth bearerAuth;

    @Getter
    @Setter
    public static class BasicAuth {
        private String username;
        private String password;
        private Charset charset = StandardCharsets.UTF_8;
    }

    @Getter
    @Setter
    public static class BearerAuth {
        private String token;
    }

}
