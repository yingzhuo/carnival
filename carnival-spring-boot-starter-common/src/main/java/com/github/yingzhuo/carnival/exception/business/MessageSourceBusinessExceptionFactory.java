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

import java.nio.charset.Charset;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author 应卓
 * @since 1.6.13
 */
public class MessageSourceBusinessExceptionFactory implements BusinessExceptionFactory {

    private final MessageSourceAccessor accessor;

    public MessageSourceBusinessExceptionFactory(MessageSourceAccessor accessor) {
        this.accessor = accessor;
    }

    public MessageSourceBusinessExceptionFactory(MessageSource messageSource) {
        accessor = new MessageSourceAccessor(messageSource);
    }

    public MessageSourceBusinessExceptionFactory(String... basenames) {
        this(basenames, UTF_8);
    }

    public MessageSourceBusinessExceptionFactory(String[] basenames, Charset encoding) {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setCacheSeconds(-1);
        messageSource.setUseCodeAsDefaultMessage(false);
        messageSource.setBasenames(basenames);
        messageSource.setDefaultEncoding(encoding.displayName());
        accessor = new MessageSourceAccessor(messageSource);
    }

    @Override
    public BusinessException create(String code, Object... args) {

        if (!StringUtils.hasText(code)) {
            throw new NoSuchCodeException(code);
        }

        try {
            return new BusinessException(code, accessor.getMessage(code, args));
        } catch (NoSuchMessageException e) {
            throw new NoSuchCodeException(code);
        }
    }

}
