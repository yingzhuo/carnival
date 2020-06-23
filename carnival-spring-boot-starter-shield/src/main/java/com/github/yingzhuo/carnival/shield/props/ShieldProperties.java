/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.shield.props;

import com.github.yingzhuo.carnival.mvc.props.AbstractWebFilterProps;
import com.github.yingzhuo.carnival.shield.core.ShieldFilter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 应卓
 * @since 1.6.21
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "carnival.shield-filter")
public class ShieldProperties extends AbstractWebFilterProps {

    private boolean enabled = true;

    public ShieldProperties() {
        super.setOrder(0);
        super.setFilterName(ShieldFilter.class.getName());
    }

}
