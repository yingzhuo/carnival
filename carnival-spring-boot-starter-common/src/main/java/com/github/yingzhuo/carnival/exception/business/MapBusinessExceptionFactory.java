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
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * @author 应卓
 * @since 1.6.3
 */
public class MapBusinessExceptionFactory implements BusinessExceptionFactory {

    private final Map<String, String> messages;
    private final MessageSourceAccessor accessor;

    public MapBusinessExceptionFactory(Map<String, String> messages, MessageSourceAccessor accessor) {
        this.messages = Collections.unmodifiableMap(new TreeMap<>(Objects.requireNonNull(messages)));
        this.accessor = accessor;
    }

    @Override
    public BusinessException create(String code, Object... args) {
        if (!StringUtils.hasText(code)) {
            throw new IllegalArgumentException("'" + code + "' is invalid code");
        }

        final String messageTemplateOrI18nCode = messages.get(code);
        if (messageTemplateOrI18nCode == null) {
            throw new IllegalArgumentException("'" + code + "' is invalid code");
        }

        if (isWithI18n(messageTemplateOrI18nCode)) {
            return doCreateWithI18n(code, messageTemplateOrI18nCode, args);
        } else {
            return doCreateWithoutI18n(code, messageTemplateOrI18nCode, args);
        }
    }

    private BusinessException doCreateWithoutI18n(String code, String messageTemplate, Object... args) {
        return new BusinessException(code, MessageFormatter.format(messageTemplate, args));
    }

    private BusinessException doCreateWithI18n(String code, String i18nCode, Object... args) {
        i18nCode = i18nCode.substring(1, i18nCode.length() - 1);    // 去掉前后的大括号
        final String message = accessor.getMessage(i18nCode, args, i18nCode);
        return new BusinessException(code, message);
    }

    private boolean isWithI18n(String messageTemplateOrI18nCode) {
        return accessor != null
                && messageTemplateOrI18nCode.startsWith("{")
                && messageTemplateOrI18nCode.endsWith("}");
    }

}
