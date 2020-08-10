/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.io;

import java.io.Serializable;
import java.util.Properties;

/**
 * @author 应卓
 * @since 1.6.6
 */
public interface ResourceProperties extends Serializable {

    public static ResourceProperties of(String location) {
        return new ResourcePropertiesImpl(location);
    }

    public Properties getProperties();

}
