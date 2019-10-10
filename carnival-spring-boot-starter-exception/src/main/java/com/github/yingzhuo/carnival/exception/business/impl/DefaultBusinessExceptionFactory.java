/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.exception.business.impl;

import com.github.yingzhuo.carnival.exception.business.BusinessException;
import com.github.yingzhuo.carnival.exception.business.BusinessExceptionFactory;
import lombok.val;
import lombok.var;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Objects;

/**
 * @author 应卓
 */
public class DefaultBusinessExceptionFactory implements BusinessExceptionFactory {

    private final Map<String, String> messages;

    public DefaultBusinessExceptionFactory(Map<String, String> messages) {
        this.messages = messages;
    }

    @Override
    public BusinessException create(String code, Object... params) {
        var message = messages.get(Objects.requireNonNull(code));

        if (!StringUtils.hasText(message)) {
            throw new IllegalArgumentException("'" + code + "' is NOT a valid code");
        }

        for (val param : params) {
            message = message.replaceFirst("\\{}", param.toString());
        }

        message = message.replaceAll("\\{}", "");

        return new BusinessException(code, message);
    }

}
