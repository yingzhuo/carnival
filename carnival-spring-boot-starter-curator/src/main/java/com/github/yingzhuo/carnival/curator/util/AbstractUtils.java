/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.curator.util;

import com.github.yingzhuo.carnival.spring.SpringUtils;
import org.apache.curator.framework.CuratorFramework;

/**
 * @author 应卓
 * @since 1.3.3
 */
abstract class AbstractUtils {

    public static CuratorFramework getClient() {
        return SpringUtils.getBean(CuratorFramework.class);
    }

}
