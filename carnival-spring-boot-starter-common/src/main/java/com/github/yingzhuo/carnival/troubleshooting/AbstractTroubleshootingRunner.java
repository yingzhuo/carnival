/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.troubleshooting;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 应卓
 * @since 1.7.6
 */
abstract class AbstractTroubleshootingRunner {

    protected static final String HIDDEN_PWD = "[****]";

    protected final boolean isHiden(String name) {
        return StringUtils.containsIgnoreCase(name, "token") ||
                StringUtils.containsIgnoreCase(name, "password") ||
                StringUtils.containsIgnoreCase(name, "secret");
    }

}
