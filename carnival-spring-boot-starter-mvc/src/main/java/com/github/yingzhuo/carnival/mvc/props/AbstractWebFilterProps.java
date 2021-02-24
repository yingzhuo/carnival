/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.props;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 应卓
 * @since 1.6.14
 */
@Getter
@Setter
public abstract class AbstractWebFilterProps {

    private String[] urlPatterns = new String[]{"/", "/*"};
    private String filterName;
    private int order = 0;

}
