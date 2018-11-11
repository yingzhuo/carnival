/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.io.ini;

import lombok.val;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * @author 应卓
 */
public class IniConverter implements Converter<String, Ini> {

    private static final ResourceLoader RESOURCE_LOADER = new DefaultResourceLoader();

    @Override
    public Ini convert(String location) {
        val resource = RESOURCE_LOADER.getResource(location);

        if (!resource.exists()) {
            val msg = String.format("Resource '%s' not found.", location);
            throw new UncheckedIOException(new IOException(msg));
        }

        return new Ini(resource);
    }

}
