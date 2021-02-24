/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.io;

import com.github.yingzhuo.carnival.spring.ResourceUtils;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Properties;

/**
 * @author 应卓
 * @since 1.7.1
 */
class ResourcePropertiesImpl implements ResourceProperties {

    private final Properties props = new Properties();

    public ResourcePropertiesImpl(String location) {
        Resource resource = ResourceUtils.loadResource(location);

        try {
            if (!resource.exists() || !resource.isReadable()) {
                throw new IOException("Cannot open resource.");
            }

            if (location.endsWith(".xml") || location.endsWith(".XML")) {
                props.loadFromXML(resource.getInputStream());
            } else {
                props.load(resource.getInputStream());
            }

            resource.getInputStream().close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Properties getProperties() {
        return this.props;
    }

    @Override
    public String toString() {
        return this.props.toString();
    }

}
