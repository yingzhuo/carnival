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

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.github.yingzhuo.carnival.common.util.MessageFormatter.format;

/**
 * @author 应卓
 * @since 1.6.3
 */
@Slf4j
public class MapBusinessExceptionFactory implements BusinessExceptionFactory, InitializingBean {

    private final Map<String, String> messages;

    public MapBusinessExceptionFactory(Map<String, String> messages) {
        this.messages = Collections.unmodifiableMap(new TreeMap<>(Objects.requireNonNull(messages)));
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

        return new BusinessException(code, format(message, params));
    }

    private Optional<String> getMessage(String code) {
        return Optional.ofNullable(messages.get(code));
    }

    @Override
    public void afterPropertiesSet() {
        if (log.isDebugEnabled() && !messages.isEmpty()) {
            log.debug("business exception codes:");
            for (final String key : messages.keySet()) {
                final String value = messages.get(key);
                log.debug("\t'{}' = '{}'", key, value);
            }
        }
    }

    public Map<String, String> getMessages() {
        return messages;
    }

}
