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

import java.util.Map;
import java.util.Optional;

/**
 * @author 应卓
 */
public class DefaultBusinessExceptionFactory extends AbstractBusinessExceptionFactory {

    private final Map<String, String> messages;

    public DefaultBusinessExceptionFactory(Map<String, String> messages) {
        this.messages = messages;
    }

    @Override
    protected Optional<String> getMessage(String code) {
        return Optional.ofNullable(messages.get(code));
    }

}
