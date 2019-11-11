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

import com.moandjiezana.toml.Toml;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.2.3
 */
public class TomlBusinessExceptionFactory extends AbstractBusinessExceptionFactory {

    private final Toml toml = new Toml();

    public TomlBusinessExceptionFactory(Resource tomlResource) {
        try {
            toml.read(tomlResource.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Optional<String> getMessage(String code) {
        return Optional.ofNullable(toml.getString(code));
    }

}
