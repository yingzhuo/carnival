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

import java.util.Objects;
import java.util.Properties;

/**
 * @author 应卓
 * @since 1.0.3
 */
public class PropertiesBusinessExceptionFactory implements BusinessExceptionFactory {

    private final Properties messages;

    public PropertiesBusinessExceptionFactory(Properties messages) {
        this.messages = messages;
    }

    @Override
    public BusinessException create(String code) {
        val message = messages.get(Objects.requireNonNull(code));
        return new BusinessException(code, Objects.requireNonNull(message).toString());
    }

}
