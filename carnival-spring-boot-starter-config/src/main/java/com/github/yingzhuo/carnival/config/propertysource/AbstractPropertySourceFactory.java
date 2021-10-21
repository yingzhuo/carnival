/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.config.propertysource;

import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author 应卓
 * @since 1.10.31
 */
public abstract class AbstractPropertySourceFactory implements PropertySourceFactory {

    private final PropertySourceLoader loader;

    public AbstractPropertySourceFactory(PropertySourceLoader loader) {
        this.loader = loader;
    }

    private String getPropertySourceName(String name) {
        if (name == null || name.isEmpty()) {
            return UUID.randomUUID().toString().replaceAll("-", "");
        }
        return name;
    }

    @Override
    public final PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        List<PropertySource<?>> list = loader.load(getPropertySourceName(name), resource.getResource());

        if (list.isEmpty()) {
            return new EmptyPropertySource();
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            throw new IOException("multiple document is NOT supported yet.");
        }
    }

    protected static class EmptyPropertySource extends PropertySource<Object> {
        public EmptyPropertySource() {
            super("empty", new Object());
        }

        @Override
        public Object getProperty(String name) {
            return null;
        }
    }

}
