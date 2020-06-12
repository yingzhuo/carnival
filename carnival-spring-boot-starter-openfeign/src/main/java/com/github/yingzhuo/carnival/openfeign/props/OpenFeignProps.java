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

/**
 * @author 应卓
 * @since 1.6.16
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "carnival.openfeign")
public class OpenFeignProps {

    private boolean enabled = true;
    private AnnotationStyle annotationStyle = AnnotationStyle.SPRING;
    private Logger.Level loggerLevel = Logger.Level.FULL;
    private String loggerName = "OpenFeign";
    private boolean decode404 = false;

    private BasicAuth basicAuth;

    public enum AnnotationStyle {
        SPRING, OPEN_FEIGN
    }

    @Getter
    @Setter
    public static class BasicAuth {
        private String username;
        private String password;
    }

}
