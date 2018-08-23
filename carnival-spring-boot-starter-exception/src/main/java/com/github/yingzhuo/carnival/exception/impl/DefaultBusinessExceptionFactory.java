/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.exception.impl;

import com.github.yingzhuo.carnival.exception.BusinessException;
import com.github.yingzhuo.carnival.exception.BusinessExceptionFactory;
import lombok.val;

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
    public BusinessException create(String code) {
        val message = messages.get(Objects.requireNonNull(code));
        return new BusinessException(code, Objects.requireNonNull(message));
    }

}
