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

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.StringUtils;

/**
 * @author 应卓
 * @since 1.6.13
 */
public class MessageSourceBusinessExceptionFactory implements BusinessExceptionFactory {

    private final MessageSourceAccessor accessor;

    public MessageSourceBusinessExceptionFactory(MessageSource messageSource) {
        accessor = new MessageSourceAccessor(messageSource);
    }

    public MessageSourceBusinessExceptionFactory(String[] basenames) {
        this(basenames, "UTF-8");
    }

    public MessageSourceBusinessExceptionFactory(String[] basenames, String encoding) {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setCacheSeconds(-1);
        messageSource.setUseCodeAsDefaultMessage(false);
        messageSource.setBasenames(basenames);
        messageSource.setDefaultEncoding(encoding);
        accessor = new MessageSourceAccessor(messageSource);
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
