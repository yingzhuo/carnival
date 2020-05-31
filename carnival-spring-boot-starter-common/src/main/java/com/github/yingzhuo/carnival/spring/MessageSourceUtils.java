/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.spring;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 应卓
 * @since 1.6.13
 */
public final class MessageSourceUtils {

    private MessageSourceUtils() {
    }

    public static String getMessage(MessageSourceResolvable messageSourceResolvable) {
        return SpringUtils.getBean(MessageSourceAccessor.class)
                .getMessage(messageSourceResolvable);
    }

    public static List<String> getMessage(Errors errors) {
        return errors.getAllErrors()
                .stream()
                .map(MessageSourceUtils::getMessage)
                .collect(Collectors.toList());
    }

    public static String getMessageAsCommaDelimitedString(Errors errors) {
        List<String> messages = getMessage(errors);
        return StringUtils.collectionToCommaDelimitedString(messages);
    }

}
