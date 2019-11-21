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

import lombok.var;
import org.ini4j.Wini;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;

/**
 * @author 应卓
 */
@Deprecated
public class IniBusinessExceptionFactory extends AbstractBusinessExceptionFactory {

    private final Wini ini;

    public IniBusinessExceptionFactory(Resource resource) {
        try {
            this.ini = new Wini(resource.getInputStream());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    protected Optional<String> getMessage(String code) {

        final String[] sectionNameAndKey = code.split("\\.");

        if (sectionNameAndKey.length != 2) {
            return Optional.empty();
        }

        var message = ini.get(sectionNameAndKey[0], sectionNameAndKey[1]);

        return Optional.ofNullable(message);
    }

}
