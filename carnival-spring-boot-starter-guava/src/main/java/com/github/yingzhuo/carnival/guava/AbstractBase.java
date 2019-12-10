/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.guava;

import com.google.common.base.Preconditions;
import com.google.common.io.Closeables;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

abstract class AbstractBase {

    private static final ResourceLoader DEFAULT_RESOURCE_LOADER = new DefaultResourceLoader();

    protected final ResourceLoader getDefaultResourceLoader() {
        return DEFAULT_RESOURCE_LOADER;
    }

    protected final Resource loadResource(String location) {

        Preconditions.checkArgument(location != null && !location.isEmpty(),
                "location is null or empty");

        return getDefaultResourceLoader().getResource(location);
    }

    protected final void close(Resource resource) {
        if (resource != null && resource.exists()) {
            try {
                InputStream inputStream = resource.getInputStream();
                Closeables.closeQuietly(inputStream);
            } catch (IOException e) {
                // NOP
            }
        }
    }

}
