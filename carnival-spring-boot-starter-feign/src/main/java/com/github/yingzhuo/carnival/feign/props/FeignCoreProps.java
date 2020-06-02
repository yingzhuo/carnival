/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.feign.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 应卓
 * @since 1.6.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "carnival.feign")
public class FeignCoreProps {

    private boolean enabled = true;
    private String defaultDatePattern = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private String defaultCalendarPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS";

}
