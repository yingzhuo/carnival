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
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.github.yingzhuo.carnival.common.util.StringFormatter.format;

/**
 * @author 应卓
 * @since 1.6.13
 */
@Slf4j
public class MapBusinessExceptionFactory implements BusinessExceptionFactory, InitializingBean {

    private final SortedMap<String, String> messages;
    private final boolean empty;

    public MapBusinessExceptionFactory(Map<String, String> messages) {
        Objects.requireNonNull(messages);
        this.messages = Collections.unmodifiableSortedMap(new TreeMap<>(messages));
        this.empty = messages.isEmpty();
    }

    @Override
    public BusinessException create(String code, Object... args) {
        if (empty) {
            throw new IllegalArgumentException(format("'{}' is invalid code", code));
        }

        if (!StringUtils.hasText(code)) {
            throw new IllegalArgumentException(format("'{}' is invalid code", code));
        }

        final String messageTemplate = messages.get(code);
        if (messageTemplate == null) {
            throw new IllegalArgumentException(format("'{}' is invalid code", code));
        }

        return new BusinessException(code, format(messageTemplate, args));
    }

    @Override
    public void afterPropertiesSet() {
        if (log.isTraceEnabled() && !messages.isEmpty()) {
            log.trace("Mapped business-exception messages (code => message template):");
            for (String code : messages.keySet()) {
                String messageTemplate = messages.get(code);
                log.trace("\t\t{} => {}", code, messageTemplate);
            }
        }
    }

}
