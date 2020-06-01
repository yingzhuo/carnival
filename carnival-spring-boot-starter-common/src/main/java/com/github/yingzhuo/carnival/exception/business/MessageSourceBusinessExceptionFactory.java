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

import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.StringUtils;

/**
 * @author 应卓
 * @since 1.6.13
 */
public class MessageSourceBusinessExceptionFactory extends ReloadableResourceBundleMessageSource implements BusinessExceptionFactory {

    private final MessageSourceAccessor accessor;

    public MessageSourceBusinessExceptionFactory() {
        setCacheSeconds(-1);
        setUseCodeAsDefaultMessage(false);
        accessor = new MessageSourceAccessor(this);
    }

    @Override
    public BusinessException create(String code, Object... args) {

        if (!StringUtils.hasText(code)) {
            throw new IllegalArgumentException("'" + code + "' is invalid code");
        }

        try {
            return new BusinessException(code, accessor.getMessage(code, args));
        } catch (NoSuchMessageException e) {
            throw new IllegalArgumentException("'" + code + "' is invalid code");
        }
    }

}
