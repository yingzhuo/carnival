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

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @author 应卓
 */
public class MapBusinessExceptionFactory extends AbstractBusinessExceptionFactory {

    private final Map<String, String> messages;

    public MapBusinessExceptionFactory(Map<String, String> messages) {
        this.messages = messages != null ?
                Collections.unmodifiableMap(messages) : Collections.emptyMap();
    }

    @Override
    protected Optional<String> getMessage(String code) {
        return Optional.ofNullable(messages.get(code));
    }

    public boolean isEmpty() {
        return messages.isEmpty();
    }

}
