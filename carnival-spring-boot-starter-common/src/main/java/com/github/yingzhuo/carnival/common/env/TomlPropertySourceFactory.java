/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.env;

import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.UUID;

/**
 * @author 应卓
 * @since 1.4.0
 */
public class TomlPropertySourceFactory implements PropertySourceFactory {

    private final TomlPropertySourceLoader loader = new TomlPropertySourceLoader();

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        if (name == null || name.isEmpty()) {
            name = UUID.randomUUID().toString();
        }
        return loader.load(name, resource.getResource()).get(0);
    }

}
