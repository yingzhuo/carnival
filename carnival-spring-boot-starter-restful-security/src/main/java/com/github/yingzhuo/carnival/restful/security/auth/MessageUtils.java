/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.auth;

import org.springframework.util.StringUtils;

/**
 * @author 应卓
 * @since 1.1.4
 */
public final class MessageUtils {

    private MessageUtils() {
    }

    public static String getMessage(String message) {
        if (!StringUtils.hasText(message) || ":::<NO MESSAGE>:::".equals(message)) {
            return null;
        }
        return message;
    }

}
