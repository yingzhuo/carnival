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

import java.util.Optional;

/**
 * @author 应卓
 * @since 1.2.3
 */
public abstract class AbstractBusinessExceptionFactory implements BusinessExceptionFactory {

    @Override
    public final BusinessException create(String code, Object... params) {

        if (!StringUtils.hasText(code)) {
            throw new IllegalArgumentException("'" + code + "' is NOT a valid code");
        }

        final Optional<String> msgOp = getMessage(code);

        if (!msgOp.isPresent()) {
            throw new IllegalArgumentException("'" + code + "' is NOT a valid code");
        }

        var message = msgOp.get();

        if (!StringUtils.hasText(message)) {
            throw new IllegalArgumentException("'" + code + "' is NOT a valid code");
        }

        for (val param : params) {
            message = message.replaceFirst("\\{}", param.toString());
        }

        message = message.replaceAll("\\{}", "");
        return new BusinessException(code, message);
    }

    protected abstract Optional<String> getMessage(String code);

}
