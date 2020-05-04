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
import lombok.var;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.6.3
 */
public class MapBusinessExceptionFactory implements BusinessExceptionFactory {

    private final Map<String, String> messages;

    public MapBusinessExceptionFactory(Map<String, String> messages) {
        this.messages = messages != null ?
                Collections.unmodifiableMap(messages) : Collections.emptyMap();
    }

    @Override
    public BusinessException create(String code, Object... params) {
        if (!StringUtils.hasText(code)) {
            throw new IllegalArgumentException("'" + code + "' is not a valid code");
        }

        final Optional<String> msgOp = getMessage(code);

        if (!msgOp.isPresent()) {
            throw new IllegalArgumentException("'" + code + "' is not a valid code");
        }

        var message = msgOp.get();

        if (!StringUtils.hasText(message)) {
            throw new IllegalArgumentException("'" + code + "' is not a valid code");
        }

        return new BusinessException(code, MessageFormatter.format(message, params));
    }

    private Optional<String> getMessage(String code) {
        return Optional.ofNullable(messages.get(code));
    }

}
