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

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.List;

/**
 * @author 应卓
 * @since 1.4.0
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

    private final YamlPropertySourceLoader loader = new YamlPropertySourceLoader();

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        List<PropertySource<?>> list = loader.load(name, resource.getResource());

        if (list.isEmpty()) {
            return new EmptyPropertySource(name);
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            throw new IllegalStateException("multiple document is NOT supported.");
        }
    }

    private static class EmptyPropertySource extends PropertySource<Object> {
        public EmptyPropertySource(String name) {
            super(name, new Object());
        }

        @Override
        public Object getProperty(String name) {
            return null;
        }
    }

}
