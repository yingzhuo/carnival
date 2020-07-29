/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.params;

import java.io.Serializable;

/**
 * @author 应卓
 * @since 1.6.30
 */
public interface Params extends Serializable {

    public String getNonce();

    public String getSign();

    public Long getTimestamp();

    public default boolean isValid() {
        return getNonce() != null &&
                getSign() != null &&
                getTimestamp() != null;
    }

}
