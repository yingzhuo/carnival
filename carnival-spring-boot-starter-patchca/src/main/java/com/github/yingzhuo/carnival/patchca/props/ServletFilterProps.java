/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.patchca.props;

import com.github.yingzhuo.carnival.patchca.PatchcaServletFilter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;

/**
 * @author 应卓
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "carnival.patchca.servlet-filter")
public class ServletFilterProps {

    private String name = PatchcaServletFilter.class.getSimpleName();
    private int order = Ordered.LOWEST_PRECEDENCE;
    private String sessionAttributeName = "PATCHCA_SESSION_ATTRIBUTE_NAME";
    private String[] urlPatterns = new String[]{"/patchca.png", "/patchca"};
    private int width = 160;
    private int height = 70;

}
