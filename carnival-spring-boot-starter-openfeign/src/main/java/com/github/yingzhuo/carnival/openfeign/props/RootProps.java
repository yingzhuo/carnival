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
public class RootProps {

    private boolean enabled = true;
    private AnnotationStyle annotationStyle = AnnotationStyle.SPRING;

    public enum AnnotationStyle {
        SPRING, OPEN_FEIGN
    }

}
