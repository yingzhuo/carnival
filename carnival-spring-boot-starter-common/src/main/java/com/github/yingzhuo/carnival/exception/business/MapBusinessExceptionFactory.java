/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.exception.business;

import com.github.yingzhuo.carnival.common.util.MessageFormatter;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author 应卓
 * @since 1.6.13
 */
public class MapBusinessExceptionFactory implements BusinessExceptionFactory {

    private final Map<String, String> messages;

    public MapBusinessExceptionFactory(Map<String, String> messages) {
        this.messages = messages;
    }

    @Override
    public BusinessException create(String code, Object... args) {
        if (!StringUtils.hasText(code)) {
            throw new IllegalArgumentException("'" + code + "' is invalid code");
        }

        final String message = messages.get(code);
        if (message == null) {
            throw new IllegalArgumentException("'" + code + "' is invalid code");
        }

        return new BusinessException(code, MessageFormatter.format(message, args));
    }

}
